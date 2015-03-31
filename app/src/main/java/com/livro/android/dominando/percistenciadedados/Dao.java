package com.livro.android.dominando.percistenciadedados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Paulo on 30/03/2015.
 */
public class Dao {
    public Context context;
    public SQLiteDatabase mSQLiteDatabase;
    //


    public Dao(Context context) {
        this.context = context;
    }

    public void abriConexao(){
        SQLiteHelper auxSqLiteHelper = new SQLiteHelper(context);
        mSQLiteDatabase = auxSqLiteHelper.getWritableDatabase();
    }
    public void fecharConexao(){
        if (this.mSQLiteDatabase != null) {
            this.mSQLiteDatabase.close();
        }
    }
}
