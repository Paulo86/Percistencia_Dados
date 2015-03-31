package com.livro.android.dominando.percistenciadedados;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Paulo on 31/03/2015.
 */
public class CadastroActivity extends Activity {
    private Context context;
    private PessoaDao pessoaDao;
    private long idPessoaAux;
    private  AlertDialog alerta;
    //
    private Button btn_salvar;
    private Button btn_cancelar;
    private Button btn_excluir;
    private EditText edt_codigo;
    private EditText edt_nome;
    private EditText edt_telefone;
    private EditText edt_data_nascimento;

    private void mapearComponenesLayout() {

        context = getBaseContext();
        pessoaDao = new PessoaDao(context);
        btn_cancelar = (Button) findViewById(R.id.cadastro_pessoa_btn_cancelar);
        btn_excluir = (Button) findViewById(R.id.cadastro_pessoa_btn_excluir);
        btn_salvar = (Button) findViewById(R.id.cadastro_pessoa_btn_salvar);
        //
        edt_codigo = (EditText) findViewById(R.id.cadastro_pessoa_et_codigo);
        edt_nome = (EditText) findViewById(R.id.cadastro_pessoa_et_nome);
        edt_telefone = (EditText) findViewById(R.id.cadastro_pessoa_et_telefone);
        edt_data_nascimento = (EditText) findViewById(R.id.cadastro_pessoa_et_datanascimento);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_pessoa_activity);
        //
        mapearComponenesLayout();
        try {
            idPessoaAux = getIntent().getLongExtra(PessoaDao.ID_PESSOA, -1);
            //
            if (idPessoaAux != -1) {
                Pessoa pessoa = pessoaDao.getPessoaById(idPessoaAux);
                //
                edt_codigo.setText(String.valueOf(pessoa.getId()));
                edt_nome.setText(pessoa.getNome());
                edt_data_nascimento.setText(pessoa.getDataNascimento());
                edt_telefone.setText(pessoa.getTelefone());
                //
                btn_excluir.setVisibility(View.VISIBLE);
            } else {
                btn_excluir.setVisibility(View.GONE);
            }
            //
            //
            btn_cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            //
            //
            btn_salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(edt_nome.getText().toString());
                    pessoa.setTelefone(edt_telefone.getText().toString());
                    pessoa.setDataNascimento(edt_data_nascimento.getText().toString());
                    //
                    if (idPessoaAux == -1) {
                        idPessoaAux = pessoaDao.getNextID();
                        pessoa.setId(idPessoaAux);
                    } else {
                        pessoa.setId(idPessoaAux);
                    }
                    //
                    pessoaDao.inserirAtualizarPessoa(pessoa);
                    edt_codigo.setText(String.valueOf(idPessoaAux));
                    Toast.makeText(context, "Cliente gravado com sucesso !", Toast.LENGTH_LONG).show();
                    btn_excluir.setVisibility(View.VISIBLE);
                }
            });
            //
            //
            btn_excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog.Builder alert = new AlertDialog.Builder(CadastroActivity.this);
                        alert.setTitle("Confirmação de Exclusão");
                        alert.setMessage("Deseja realmente excluir este Cliente ?");
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pessoaDao.deletarPessoa(idPessoaAux);
                                Toast.makeText(context, "Cliente Excluido com sucesso !", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        });
                        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onBackPressed();
                            }
                        });
                        alert.show();
                        //
                        alerta = alert.create();
                        alerta.show();


                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //
    //

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        //
        super.onBackPressed();
    }
}
