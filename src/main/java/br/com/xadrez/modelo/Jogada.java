package br.com.xadrez.modelo;

public class Jogada {
    private Posicao de;
    private Posicao para;

    public Jogada(Posicao de, Posicao para) {
        this.de = de;
        this.para = para;
    }

    public Posicao getDe() {
        return de;
    }

    public Posicao getPara() {
        return para;
    }
}
