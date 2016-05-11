package com.example.inno_09.realmstudy;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;


public class SecondFragment extends Fragment {

    Realm realm;
    RealmResults<MyListRealm> result;

    RealmRecyclerView recyclerView;
    MyAdapter myAdapter;

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
        realm = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);

        result = realm.where(MyListRealm.class).findAll();

        recyclerView = (RealmRecyclerView) v.findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter(getContext(), result, true, true);
        recyclerView.setAdapter(myAdapter);

        return v;
    }

    public class MyAdapter extends RealmBasedRecyclerViewAdapter<MyListRealm, ViewHolder> {

        public MyAdapter(
                Context context,
                RealmResults<MyListRealm> realmResults,
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
            MyListRealm item = realmResults.get(i);
            viewHolder.setViewHolder(item);
        }
    }

    class ViewHolder extends RealmViewHolder {

        MyListRealm item;
        TextView tvName, tvScore;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvScore = (TextView) itemView.findViewById(R.id.tvScore);
        }

        public void setViewHolder(MyListRealm item) {
            this.item = item;
            tvName.setText(item.getName());

            int total=0;
            for(MyRealm myRealm : item.getRealmList()) {
                total+=myRealm.getScore();
            }

            tvScore.setText("" + total);
        }
    }

}
