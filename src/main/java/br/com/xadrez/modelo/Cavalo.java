package br.com.xadrez.modelo;

public class Cavalo extends Peca {

    public Cavalo(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♘" : "♞";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        int[][] possiveisMovimentos = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] movimento : possiveisMovimentos) {
            p.setLinha(posicao.getLinha() + movimento[0]);
            p.setColuna(posicao.getColuna() + movimento[1]);

            if (getTabuleiro().posicaoValida(p) && (!getTabuleiro().temPeca(p) || getTabuleiro().temPecaAdversaria(p, this))) {
                movimentos[p.getLinha()][p.getColuna()] = true;
            }
        }

        return movimentos;
    }

    @Override
    public int getValor() {
        return 3;
    }
}
