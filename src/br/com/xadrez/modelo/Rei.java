package br.com.xadrez.modelo;

public class Rei extends Peca {

    public Rei(Cor cor) {
        super(cor);
    }

    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♔" : "♚";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                p.setLinha(posicao.getLinha() + i);
                p.setColuna(posicao.getColuna() + j);

                if (getTabuleiro().posicaoValida(p) && (!getTabuleiro().temPeca(p) || getTabuleiro().temPecaAdversaria(p, this))) {
                    movimentos[p.getLinha()][p.getColuna()] = true;
                }
            }
        }

        return movimentos;
    }
}
