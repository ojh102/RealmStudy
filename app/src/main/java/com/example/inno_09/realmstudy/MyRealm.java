package com.example.inno_09.realmstudy;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by INNO-09 on 2016-05-09.
 */
public class MyRealm extends RealmObject{

    /**
     private instance 필드 만을 허용
     기본 getter, setter method만 허용
     public 이나 private에 대해 static 필드만 허용
     Static method 만 허용
     method가 없는 interface의 구현 만 허용
     **/

    @PrimaryKey
    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
