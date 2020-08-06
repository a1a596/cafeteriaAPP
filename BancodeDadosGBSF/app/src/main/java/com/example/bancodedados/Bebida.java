package com.example.bancodedados;

public class Bebida {
    private String nome;
    private String descricao;
    private int imagemIDRecurso;

    private Bebida(String name, String descricao, int imagemIDRecurso) {
        this.nome = name;
        this.descricao = descricao;
        this.imagemIDRecurso = imagemIDRecurso;
    }

    public String getDescricao() {
        return descricao;
    }
    public String getNome() {
        return nome;
    }
    public int getImagemIDRecurso() {
        return imagemIDRecurso;
    }
    public String toString() {
        return this.getNome();

    }
}