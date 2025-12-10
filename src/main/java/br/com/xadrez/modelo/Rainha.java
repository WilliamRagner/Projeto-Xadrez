package br.com.xadrez.modelo;

public class Rainha extends Peca {

    public Rainha(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♕" : "♛";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        
        // Movimento para cima
        Posicao p = new Posicao(getPosicao().getLinha() - 1, getPosicao().getColuna());
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p = new Posicao(p.getLinha() - 1, p.getColuna());
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Movimento para baixo
        p = new Posicao(getPosicao().getLinha() + 1, getPosicao().getColuna());
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p = new Posicao(p.getLinha() + 1, p.getColuna());
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Movimento para a esquerda
        p = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() - 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p = new Posicao(p.getLinha(), p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Movimento para a direita
        p = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() + 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p = new Posicao(p.getLinha(), p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Noroeste
        p = new Posicao(getPosicao().getLinha() - 1, getPosicao().getColuna() - 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p = new Posicao(p.getLinha() - 1, p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Nordeste
        p = new Posicao(getPosicao().getLinha() - 1, getPosicao().getColuna() + 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p = new Posicao(p.getLinha() - 1, p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Sudoeste
        p = new Posicao(getPosicao().getLinha() + 1, getPosicao().getColuna() - 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p = new Posicao(p.getLinha() + 1, p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Sudeste
        p = new Posicao(getPosicao().getLinha() + 1, getPosicao().getColuna() + 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p = new Posicao(p.getLinha() + 1, p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        return movimentos;
    }

    @Override
    public int getValor() {
        return 9;
    }
}
