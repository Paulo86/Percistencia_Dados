package com.livro.android.dominando.percistenciadedados;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    TextView tv_nome;
    Context context;
    ListView lv_opcoes;
    String[] opcoes = new String[]{"Cadastro Novo Cliente", "Clientes Cadastrados", "" +
            "Sincronizar Clientes (Remarca)", "Deletar todos Clientes", "Sair"};


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
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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

                    case 2:
                        SincronizaClientes sincronizaClientes = new SincronizaClientes();
                        sincronizaClientes.execute();
                        break;

                    case 3:
                        try {
                            PessoaDao pessoaDao = new PessoaDao(context);
                            pessoaDao.deletarAll();
                            Toast.makeText(context, "Clientes deletados.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        finish();
                        break;
                }
            }
        });
        //

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

    public class SincronizaClientes extends AsyncTask<Void, Integer, Void> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPreExecute() {
            dialog.setTitle("Sincronizando");
            dialog.setMessage("Aguarde, atualizando Banco de dados...");
            dialog.create();
            dialog.show();
            //
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            PessoaDao pessoaDao = new PessoaDao(context);
            ClienteSQLServer clienteSQLServer = new ClienteSQLServer();
            pessoaDao.inserirListPessoa(clienteSQLServer.ListClienteAll());
            //
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();
            Toast.makeText(context, "Sincronismo Concluido.", Toast.LENGTH_SHORT).show();
        }
    }


}


