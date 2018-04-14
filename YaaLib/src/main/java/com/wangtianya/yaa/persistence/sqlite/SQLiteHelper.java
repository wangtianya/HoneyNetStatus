
package com.wangtianya.yaa.persistence.sqlite;

import java.io.File;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tianya on 15/4/22.
 */
public class SQLiteHelper {

    public static SQLiteDatabase openDatabase(File file) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file, null);
        return sqLiteDatabase;
    }


}
