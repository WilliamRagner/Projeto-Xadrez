package br.com.xadrez.modelo;

public class Cavalo extends Peca {

    public Cavalo(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♘" : "♞";
    }
}
