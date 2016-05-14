package com.example.inno_09.realmstudy;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by INNO-09 on 2016-05-11.
 */
public class MyListRealm extends RealmObject {
    @PrimaryKey
    private String name;
    private RealmList<MyRealm> realmList;

    public RealmList<MyRealm> getRealmList() {
        return realmList;
    }

    public void setRealmList(RealmList<MyRealm> realmList) {
        this.realmList = realmList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
