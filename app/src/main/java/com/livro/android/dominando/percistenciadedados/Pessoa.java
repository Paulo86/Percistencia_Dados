package com.livro.android.dominando.percistenciadedados;

/**
 * Created by Paulo on 30/03/2015.
 */
public class Pessoa {
    private long id;
    private String nome;
    private String dataNascimento;
    private String telefone;
    //
    //
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
