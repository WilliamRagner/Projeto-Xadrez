package br.com.xadrez.modelo;

public class Rainha extends Peca {

    public Rainha(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♕" : "♛";
    }
}
