package br.com.xadrez.modelo;

public class Bispo extends Peca {

    public Bispo(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♗" : "♝";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        // Noroeste
        p.setLinha(posicao.getLinha() - 1);
        p.setColuna(posicao.getColuna() - 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
            p.setColuna(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Nordeste
        p.setLinha(posicao.getLinha() - 1);
        p.setColuna(posicao.getColuna() + 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
            p.setColuna(p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Sudoeste
        p.setLinha(posicao.getLinha() + 1);
        p.setColuna(posicao.getColuna() - 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
            p.setColuna(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Sudeste
        p.setLinha(posicao.getLinha() + 1);
        p.setColuna(posicao.getColuna() + 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
            p.setColuna(p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        return movimentos;
    }

    @Override
    public int getValor() {
        return 3;
    }
}
