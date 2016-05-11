package com.example.inno_09.realmstudy;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by INNO-09 on 2016-05-09.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        RealmConfiguration config = new RealmConfiguration.Builder(context)
//                .name("myrealm.realm")
//                .encryptionKey(getKey())
//                .schemaVersion(42)
//                .setModules(new MySchemaModule())
//                .migration(new MyMigration())
//                .build();

//        RealmConfiguration myConfig = new RealmConfiguration.Builder(context)
//                .name("myrealm.realm").
//        .schemaVersion(2)
//                .setModules(new MyCustomSchema())
//                .build();
//
//        RealmConfiguration otherConfig = new RealmConfiguration.Builder(context)
//                .name("otherrealm.realm")
//                .schemaVersion(5)
//                .setModules(new MyOtherSchema())
//                .build();
//
//        Realm myRealm = Realm.getInstance(myConfig);
//        Realm otherRealm = Realm.getInstance(otherConfig);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
