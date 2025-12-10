package br.com.xadrez.modelo;

public class Rei extends Peca {

    private int movimentos;

    public Rei(Cor cor) {
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
        return getCor() == Cor.BRANCA ? "♔" : "♚";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                Posicao p = new Posicao(getPosicao().getLinha() + i, getPosicao().getColuna() + j);

                if (getTabuleiro().posicaoValida(p) && (!getTabuleiro().temPeca(p) || getTabuleiro().temPecaAdversaria(p, this))) {
                    movimentos[p.getLinha()][p.getColuna()] = true;
                }
            }
        }

        // Roque
        if (getMovimentos() == 0) {
            // Roque pequeno
            Posicao posTorre1 = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() + 3);
            if (getTabuleiro().posicaoValida(posTorre1) && getTabuleiro().temPeca(posTorre1) && getTabuleiro().getPeca(posTorre1) instanceof Torre && ((Torre) getTabuleiro().getPeca(posTorre1)).getMovimentos() == 0) {
                Posicao p1 = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() + 1);
                Posicao p2 = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() + 2);
                if (getTabuleiro().getPeca(p1) == null && getTabuleiro().getPeca(p2) == null) {
                    movimentos[getPosicao().getLinha()][getPosicao().getColuna() + 2] = true;
                }
            }

            // Roque grande
            Posicao posTorre2 = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() - 4);
            if (getTabuleiro().posicaoValida(posTorre2) && getTabuleiro().temPeca(posTorre2) && getTabuleiro().getPeca(posTorre2) instanceof Torre && ((Torre) getTabuleiro().getPeca(posTorre2)).getMovimentos() == 0) {
                Posicao p1 = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() - 1);
                Posicao p2 = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() - 2);
                Posicao p3 = new Posicao(getPosicao().getLinha(), getPosicao().getColuna() - 3);
                if (getTabuleiro().getPeca(p1) == null && getTabuleiro().getPeca(p2) == null && getTabuleiro().getPeca(p3) == null) {
                    movimentos[getPosicao().getLinha()][getPosicao().getColuna() - 2] = true;
                }
            }
        }

        return movimentos;
    }

    @Override
    public int getValor() {
        return 10000;
    }
}
