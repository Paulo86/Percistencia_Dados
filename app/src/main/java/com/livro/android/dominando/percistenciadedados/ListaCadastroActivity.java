package com.livro.android.dominando.percistenciadedados;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Paulo on 31/03/2015.
 */
public class ListaCadastroActivity extends Activity {
    Context context;
    ArrayList<HashMap<String, String>> listPessoa;
    ListView lv_lista_pessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_pessoa_activity);
        //
        context = getBaseContext();
        lv_lista_pessoas = (ListView)findViewById(R.id.lv_lista_pessoa);
        listPessoa = new ArrayList<>();
        //
        PessoaDao dao = new PessoaDao(context);
        listPessoa = dao.pessoaListAll_HM();
        //
        String[] from = {PessoaDao.ID_PESSOA, PessoaDao.NM_PESSOA};
        int[] to = {R.id.celula_tv_id_pessoa, R.id.celula_tv_nome_pessoa};
        SimpleAdapter mAdapter = new SimpleAdapter(context,listPessoa, R.layout.item_lista_pessoa, from, to);
        //
        lv_lista_pessoas.setAdapter(mAdapter);
        //
        if (listPessoa.size() <= 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ListaCadastroActivity. this);
            alert.setMessage("Nenhum Cliente encontrado.\nDeseja ir para tela de cadastro ?");
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(context, CadastroActivity.class));
                }
            });
            alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();
                }
            });
            alert.create();
            alert.show();
        }
        //
        lv_lista_pessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //
                HashMap<String, String> item = (HashMap<String, String>) adapterView.getItemAtPosition(position);
                //
                Intent intent = new Intent(context, CadastroActivity.class);
                intent.putExtra(PessoaDao.ID_PESSOA, Long.parseLong(item.get(PessoaDao.ID_PESSOA)));
                //
                startActivity(intent);
                //Toast.makeText(context, "ID " + item.get(PessoaDao.ID_PESSOA), Toast.LENGTH_SHORT).show();
                //
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        //
        super.onBackPressed();
    }
}
