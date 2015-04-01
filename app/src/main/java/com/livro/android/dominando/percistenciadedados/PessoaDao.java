package com.livro.android.dominando.percistenciadedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Paulo on 30/03/2015.
 */
public class PessoaDao extends Dao {
    public static final String ID_PESSOA = "id_pessoa";
    public static final String NM_PESSOA = "nm_pessoa";
    public static final String DT_NASCIMENTO = "dt_nascimento";
    public static final String NR_TELEFONE = "nr_telefone";
    //
    public static final String TB_NAME = "tb_cliente";

    public PessoaDao(Context context) {
        super(context);
    }

    public long getNextID() {
        long auxID = 0;
        //
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT MAX(id_pessoa) + 1 AS id_pessoa FROM tb_cliente");
        Cursor cursor = null;
        abriConexao();
        cursor = mSQLiteDatabase.rawQuery(sbSql.toString().toLowerCase(), null);
        //
        while (cursor.moveToNext()) {
            auxID = cursor.getLong(0);
        }
        cursor.close();
        //
        if (auxID == 0) {
            auxID = 1;
        }
        //
        fecharConexao();
        return auxID;
    }

    public void inserirAtualizarPessoa(Pessoa pessoa) {
        abriConexao();
        //
        ContentValues values = new ContentValues();
        values.put(ID_PESSOA, pessoa.getId());
        values.put(NM_PESSOA, pessoa.getNome());
        values.put(DT_NASCIMENTO, pessoa.getDataNascimento());
        values.put(NR_TELEFONE, pessoa.getTelefone());
        //
        if (mSQLiteDatabase.insert(TB_NAME, null, values) == -1) {
            StringBuilder sbWhere = new StringBuilder();
            sbWhere.append(ID_PESSOA).append(" = '").append(pessoa.getId()).append(" '");
            mSQLiteDatabase.update(TB_NAME.toLowerCase(), values, sbWhere.toString().toLowerCase(), null);
        }
        //
        fecharConexao();
    }

    public void deletarPessoa(long idPessoa) {
        abriConexao();
        //
        StringBuilder sbWhere = new StringBuilder();
        sbWhere.append(ID_PESSOA).append(" = '").append(idPessoa).append(" '");
        //
        mSQLiteDatabase.delete(TB_NAME, sbWhere.toString().toLowerCase(), null);
        //
        fecharConexao();

    }

    public Pessoa getPessoaById(long idPessoa) {
        Pessoa pessoa = null;
        //
        abriConexao();
        //
        StringBuilder sbWhere = new StringBuilder();
        sbWhere.append("SELECT * FROM [").append(TB_NAME).append("] WHERE [")
                .append(ID_PESSOA).append("] = '").append(idPessoa).append(" '");
        //
        Cursor cursor = null;
        cursor = mSQLiteDatabase.rawQuery(sbWhere.toString().toLowerCase(), null);
        //
        while (cursor.moveToNext()) {
            pessoa = new Pessoa();
            pessoa.setId(cursor.getLong(cursor.getColumnIndex(ID_PESSOA)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NM_PESSOA)));
            pessoa.setTelefone(cursor.getString(cursor.getColumnIndex(NR_TELEFONE)));
            pessoa.setDataNascimento(cursor.getString(cursor.getColumnIndex(DT_NASCIMENTO)));
        }
        cursor.close();
        //
        fecharConexao();
        //
        return pessoa;
    }

    public ArrayList<HashMap<String, String>> pessoaListAll_HM() {
        ArrayList<HashMap<String, String>> pessoas = new ArrayList<HashMap<String, String>>();
        //
        abriConexao();
        //
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT * FROM [").append(TB_NAME).append("] ORDER BY ").append(NM_PESSOA);
        //
        Cursor cursor = null;
        cursor = mSQLiteDatabase.rawQuery(sbSql.toString().toLowerCase(), null);
        //
        while (cursor.moveToNext()) {
            HashMap<String, String> pessoa = new HashMap<String, String>();
            //
            pessoa.put(ID_PESSOA, String.valueOf(cursor.getLong(cursor.getColumnIndex(ID_PESSOA))));
            pessoa.put(NM_PESSOA, cursor.getString(cursor.getColumnIndex(NM_PESSOA)));
            //
            pessoas.add(pessoa);
        }
        cursor.close();
        //
        fecharConexao();
        //
        return pessoas;
    }

    public void inserirListPessoa(ArrayList<Pessoa> listPessoa) {
        //
        abriConexao();
        //
        Pessoa pessoa = new Pessoa();
        ContentValues values = new ContentValues();
        //
        try {

            for (int i = 0; i < listPessoa.size(); i++) {

                values.put(ID_PESSOA, listPessoa.get(i).getId());
                values.put(NM_PESSOA, listPessoa.get(i).getNome());
                values.put(NR_TELEFONE, listPessoa.get(i).getTelefone());
                values.put(DT_NASCIMENTO, listPessoa.get(i).getDataNascimento());
                //
                if (mSQLiteDatabase.insert(TB_NAME, null, values) == -1) {
                    StringBuilder sbWhere = new StringBuilder();
                    sbWhere.append(ID_PESSOA).append(" = '").append(pessoa.getId()).append(" '");
                    mSQLiteDatabase.update(TB_NAME.toLowerCase(), values, sbWhere.toString().toLowerCase(), null);
                }
            }

        } catch (Exception e) {

        }
        //
        fecharConexao();
    }

    public void deletarAll(){
        abriConexao();
        //
        mSQLiteDatabase.delete(TB_NAME,null, null);
        //
        fecharConexao();
    }

}
