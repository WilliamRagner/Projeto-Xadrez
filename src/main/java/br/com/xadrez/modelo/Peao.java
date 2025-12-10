package br.com.xadrez.modelo;

public class Peao extends Peca {
    private static final int VALOR_PEAO = 1;
    private int movimentos = 0;

    public Peao(Cor cor) {
        super(cor);
    }

    public void incrementaMovimentos() {
        movimentos++;
    }


    @Override
    public String getSimbolo() {
        return getCor() == Cor.BRANCA ? "♙" : "♟";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matrizMovimentos = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        int direcao = (getCor() == Cor.BRANCA) ? -1 : 1;

        verificarMovimentoParaFrente(matrizMovimentos, direcao);
        verificarPrimeiroMovimento(matrizMovimentos, direcao);
        verificarCapturas(matrizMovimentos, direcao);
        verificarEnPassant(matrizMovimentos, direcao);

        return matrizMovimentos;
    }

    private void verificarMovimentoParaFrente(boolean[][] matriz, int direcao) {
        Posicao p = new Posicao(getPosicao().getLinha() + direcao, getPosicao().getColuna());
        if (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void verificarPrimeiroMovimento(boolean[][] matriz, int direcao) {
        if (this.movimentos == 0) {
            Posicao p1 = new Posicao(getPosicao().getLinha() + direcao, getPosicao().getColuna());
            Posicao p2 = new Posicao(getPosicao().getLinha() + 2 * direcao, getPosicao().getColuna());
            if (getTabuleiro().posicaoValida(p2) && !getTabuleiro().temPeca(p2) && getTabuleiro().posicaoValida(p1) && !getTabuleiro().temPeca(p1)) {
                matriz[p2.getLinha()][p2.getColuna()] = true;
            }
        }
    }

    private void verificarCapturas(boolean[][] matriz, int direcao) {
        Posicao pEsquerda = new Posicao(getPosicao().getLinha() + direcao, getPosicao().getColuna() - 1);
        if (getTabuleiro().posicaoValida(pEsquerda) && getTabuleiro().temPecaAdversaria(pEsquerda, this)) {
            matriz[pEsquerda.getLinha()][pEsquerda.getColuna()] = true;
        }

        Posicao pDireita = new Posicao(getPosicao().getLinha() + direcao, getPosicao().getColuna() + 1);
        if (getTabuleiro().posicaoValida(pDireita) && getTabuleiro().temPecaAdversaria(pDireita, this)) {
            matriz[pDireita.getLinha()][pDireita.getColuna()] = true;
        }
    }

    private void verificarEnPassant(boolean[][] matriz, int direcao) {
        if (getTabuleiro().getPeaoVulneravelEnPassant() != null) {
            if (getPosicao().getLinha() == (getCor() == Cor.BRANCA ? 3 : 4)) {
                Posicao posPeaoVulneravel = getTabuleiro().getPeaoVulneravelEnPassant().getPosicao();
                if (posPeaoVulneravel.getColuna() == getPosicao().getColuna() - 1) {
                    matriz[posPeaoVulneravel.getLinha() + direcao][posPeaoVulneravel.getColuna()] = true;
                }
                if (posPeaoVulneravel.getColuna() == getPosicao().getColuna() + 1) {
                    matriz[posPeaoVulneravel.getLinha() + direcao][posPeaoVulneravel.getColuna()] = true;
                }
            }
        }
    }

    @Override
    public int getValor() {
        return VALOR_PEAO;
    }
}
