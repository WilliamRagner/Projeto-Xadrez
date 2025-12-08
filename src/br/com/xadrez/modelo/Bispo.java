package br.com.xadrez.modelo;

public class Bispo extends Peca {

    public Bispo(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♗" : "♝";
    }
}
