package com.example.inno_09.realmstudy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    RealmRecyclerView recyclerView;
    MyAdapter myAdapter;
    EditText edtName, edtScore;
    TextView tvSum;
    Button btnAdd, btnSort, btnDel;

    Context mContext;
    private Realm realm;
    private RealmResults<MyRealm> result;
    private RealmChangeListener realmListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        realm = Realm.getDefaultInstance();

        init();

        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmListener);
//        realm.removeAllChangeListeners();
        realm.close();
        realm = null;
    }

    public void getData() {

        result = realm.where(MyRealm.class).findAll();
        refreshView();

        int sum = result.sum("score").intValue();
        tvSum.setText("" + sum);


        realmListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                int sum = result.sum("score").intValue();
                tvSum.setText("" + sum);
                Log.d("ojh", "change");
            }
        };
        result.addChangeListener(realmListener);

    }

    public void init() {
        recyclerView = (RealmRecyclerView) findViewById(R.id.recyclerView);

        tvSum = (TextView) findViewById(R.id.tvSum);

        edtName = (EditText) findViewById(R.id.edtName);
        edtScore = (EditText) findViewById(R.id.edtScore);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSort = (Button) findViewById(R.id.btnSort);
        btnDel = (Button) findViewById(R.id.btnDel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = edtName.getText().toString();
                final String score = edtScore.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(score)) {

//                    방법 1.
//                    realm.beginTransaction();
//                    realm.commitTransaction();
//                    방법 2.
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//
//                        }
//                    });

                    // 방법 3.
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            MyRealm myItem = realm.createObject(MyRealm.class);
                            myItem.setName(name);
                            myItem.setScore(Integer.parseInt(score));
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(mContext, "Add!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "제대로 입력바람", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                result = realm.where(MyRealm.class)
                        .equalTo("name", edtName.getText().toString())
                        .or()
                        .contains("name", "오재환")
                        .findAll();

                refreshView();

            }
        });

        btnSort.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                result = realm.where(MyRealm.class).findAllSortedAsync("score", Sort.DESCENDING);

                refreshView();

                return true;
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmQuery<MyRealm> query = realm.where(MyRealm.class);

                query.equalTo("name",edtName.getText().toString());

                result = query.findAll();

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // remove single match
                        //result.deleteFromRealm(0);
                        //result.deleteLastFromRealm();

                        // remove a single object
                        MyRealm data = result.get(0);
                        data.deleteFromRealm();
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(mContext, "Add!", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(mContext, "Add!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                result=realm.where(MyRealm.class).findAll();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        result.deleteAllFromRealm();
                    }
                });

                return true;
            }
        });

    }

    public void refreshView() {
        myAdapter = new MyAdapter(mContext, result, true, true);
        recyclerView.setAdapter(myAdapter);
    }

    public class MyAdapter extends RealmBasedRecyclerViewAdapter<MyRealm, ViewHolder> {

        public MyAdapter(
                Context context,
                RealmResults<MyRealm> realmResults,
                boolean automaticUpdate,
                boolean animateIdType) {
            super(context, realmResults, automaticUpdate, animateIdType);
        }


        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
            View v = inflater.inflate(R.layout.view_item, viewGroup, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
            MyRealm item = realmResults.get(i);
            viewHolder.setViewHolder(item);
        }
    }

    class ViewHolder extends RealmViewHolder {

        MyRealm item;
        TextView tvName, tvScore;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvScore = (TextView) itemView.findViewById(R.id.tvScore);
        }

        public void setViewHolder(MyRealm item) {
            this.item = item;
            tvName.setText(item.getName());
            tvScore.setText("" + item.getScore());
        }
    }

}
