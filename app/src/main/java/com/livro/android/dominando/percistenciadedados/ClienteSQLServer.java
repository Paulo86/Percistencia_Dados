package com.livro.android.dominando.percistenciadedados;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Paulo on 31/03/2015.
 */
public class ClienteSQLServer {
    Connection connection;

    public ClienteSQLServer() {
        SQLServerHelper da = new SQLServerHelper();

        try {
            connection = da.criaConexao("remarca.dyndns.org", "SGRV2", "sa", "remarca");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pessoa> ListClienteAll() {
        ResultSet resultSet;
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT idcliente, razaosocial, telefone, datacadastro FROM Cliente");
        ArrayList<Pessoa> listPessoa = new ArrayList<Pessoa>();
        //
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sbSql.toString().toLowerCase());
            //
            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getLong("idcliente"));
                pessoa.setNome(resultSet.getString("razaosocial"));
                pessoa.setDataNascimento(resultSet.getDate("datacadastro").toString());
                pessoa.setTelefone(resultSet.getString("telefone"));
                //
                listPessoa.add(pessoa);
            }
            //
            resultSet.close();

        } catch (Exception e) {

        }
        //
        return listPessoa;
    }
}
