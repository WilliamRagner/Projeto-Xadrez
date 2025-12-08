package br.com.xadrez.modelo;

public abstract class Peca {
    
    private final Cor cor;

    public Peca(Cor cor) {
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public abstract String getSimbolo();
}
