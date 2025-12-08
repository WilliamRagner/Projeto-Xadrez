package br.com.xadrez.modelo;

public class Peao extends Peca {

    public Peao(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♙" : "♟";
    }
}
