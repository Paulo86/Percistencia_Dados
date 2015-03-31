package com.livro.android.dominando.percistenciadedados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paulo on 30/03/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "CADASTRO";
    public static final int DB_VERSION = 1;


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("CREATE TABLE IF NOT EXISTS [tb_cliente] (");
        sbSql.append("  [id_pessoa] INTEGER PRIMARY KEY NOT NULL,");
        sbSql.append("  [nm_pessoa] VARCHAR(100) NOT NULL,");
        sbSql.append("  [dt_nascimento] DATE NOT NULL,");
        sbSql.append("  [nr_telefone] VARCHAR(20) NOT NULL);");
        //
        sqLiteDatabase.execSQL(sbSql.toString().toLowerCase());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("DROP TABLE IF EXISTS [tb_cliente]");
        sqLiteDatabase.execSQL(sbSql.toString().toLowerCase());
        //
        onCreate(sqLiteDatabase);
    }
}
