package br.com.xadrez.modelo;

public abstract class Peca {
    
    private final Cor cor;
    protected Posicao posicao;
    protected Tabuleiro tabuleiro;


    public Peca(Cor cor) {
        this.cor = cor;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Cor getCor() {
        return cor;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public abstract String getSimbolo();
    
    public abstract boolean[][] movimentosPossiveis();

    public abstract int getValor();
}
