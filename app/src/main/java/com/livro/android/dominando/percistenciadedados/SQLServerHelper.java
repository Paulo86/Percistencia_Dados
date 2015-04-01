package com.livro.android.dominando.percistenciadedados;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Paulo on 11/02/2015.
 */
public class SQLServerHelper {
    //
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public Connection criaConexao(String dbHost, String dbName, String dbUser, String dbPassword) throws Exception {
        //
        Connection conn = null;
        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder sqlConnectionString = new StringBuilder();
        sqlConnectionString.append("jdbc:jtds:sqlserver://");
        sqlConnectionString.append(dbHost);
        sqlConnectionString.append(":1433;databaseName=");
        sqlConnectionString.append(dbName);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(sqlConnectionString.toString(), dbUser, dbPassword);

        } catch (Exception e) {
            throw new Exception(e.getMessage().toString());
        }
        //
        return  conn;
    }
}
