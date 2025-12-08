package br.com.xadrez.modelo;

public class Rei extends Peca {

    public Rei(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♔" : "♚";
    }
}
