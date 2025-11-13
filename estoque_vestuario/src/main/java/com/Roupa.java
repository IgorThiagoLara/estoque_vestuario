package com;

public class Roupa {
    private int id;
    private String nome;
    private String cor;
    private String tamanho;
    private int quantidade;

    public Roupa(int id, String nome, String cor, String tamanho, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Cor: %s | Tamanho: %s | Quantidade: %d",
                id, nome, cor, tamanho, quantidade);
    }
}