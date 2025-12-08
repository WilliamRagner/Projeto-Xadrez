package br.com.xadrez.modelo;

public class Torre extends Peca {

    public Torre(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♖" : "♜";
    }
}
