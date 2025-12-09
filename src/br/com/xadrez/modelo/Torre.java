package br.com.xadrez.modelo;

public class Torre extends Peca {
    private int movimentos;
    public Torre(Cor cor) {
        super(cor);
        this.movimentos = 0;
    }
    public void incrementaMovimentos() {
        movimentos++;
    }
    public int getMovimentos() {
        return movimentos;
    }


    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♖" : "♜";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        // Movimento para cima
        p.setLinha(posicao.getLinha() - 1);
        p.setColuna(posicao.getColuna());
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Movimento para baixo
        p.setLinha(posicao.getLinha() + 1);
        p.setColuna(posicao.getColuna());
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Movimento para a esquerda
        p.setLinha(posicao.getLinha());
        p.setColuna(posicao.getColuna() - 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Movimento para a direita
        p.setLinha(posicao.getLinha());
        p.setColuna(posicao.getColuna() + 1);
        while (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            movimentos[p.getLinha()][p.getColuna()] = true;
        }

        return movimentos;
    }

    @Override
    public int getValor() {
        return 5;
    }
}
