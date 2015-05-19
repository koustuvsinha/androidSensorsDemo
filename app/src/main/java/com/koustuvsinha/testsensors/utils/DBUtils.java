package com.koustuvsinha.testsensors.utils;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by koustuv on 19/5/15.
 */
public class DBUtils {

    private Realm realm;

    public DBUtils(Context context) {
        this.realm = Realm.getInstance(context);
    }

    public Realm getRealm() {
        return realm;
    }

    public void closeDb() {
        realm.close();
    }


}
