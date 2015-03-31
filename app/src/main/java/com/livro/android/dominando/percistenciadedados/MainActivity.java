package com.livro.android.dominando.percistenciadedados;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    TextView tv_nome;
    Context context;
    ListView lv_opcoes;
    String[] opcoes = new String[] { "Cadastro Novo Cliente", "Clientes Cadastrados", "Sair" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        context = getBaseContext();
        lv_opcoes = (ListView) findViewById(R.id.lv_opcoes);
        //
        lv_opcoes.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, opcoes));
        lv_opcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(context, CadastroActivity.class));
                        finish();
                        break;

                    case 1:
                        startActivity(new Intent(context, ListaCadastroActivity.class));
                        finish();
                        break;

                    default:
                        finish();
                        break;
                }
            }
        });
    }

    private void testeInserirAtualizar() {
        long id = 0;
        Pessoa pessoa = new Pessoa();
        PessoaDao pessoaDao = new PessoaDao(getBaseContext());
        //
        id = pessoaDao.getNextID();
        pessoa.setNome("Paulo Lima");
        pessoa.setTelefone("22222");
        pessoa.setDataNascimento("11/11/1111");
        pessoa.setId(id);
        //
        pessoaDao.inserirAtualizarPessoa(pessoa);
        Toast.makeText(getBaseContext(), "Pessoa inserirda", Toast.LENGTH_LONG).show();
        //
        String nome = pessoaDao.getPessoaById(id).getNome();
        Toast.makeText(getBaseContext(), "Pessoa recuperada do banco: " + nome, Toast.LENGTH_LONG).show();
    }
}
