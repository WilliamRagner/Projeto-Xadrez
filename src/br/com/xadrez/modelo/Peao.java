package br.com.xadrez.modelo;

public class Peao extends Peca {
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
        Posicao p = new Posicao(0, 0);

        int direcao = (getCor() == Cor.BRANCA) ? -1 : 1;

        // Movimento para frente
        p.setLinha(posicao.getLinha() + direcao);
        p.setColuna(posicao.getColuna());
        if (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p)) {
            matrizMovimentos[p.getLinha()][p.getColuna()] = true;
        }

        // Primeiro movimento
        if (this.movimentos == 0) {
            p.setLinha(posicao.getLinha() + 2 * direcao);
            p.setColuna(posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + direcao, posicao.getColuna());
            if (getTabuleiro().posicaoValida(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoValida(p2) && !getTabuleiro().temPeca(p2)) {
                matrizMovimentos[p.getLinha()][p.getColuna()] = true;
            }
        }

        // Captura
        p.setLinha(posicao.getLinha() + direcao);
        p.setColuna(posicao.getColuna() - 1);
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            matrizMovimentos[p.getLinha()][p.getColuna()] = true;
        }

        p.setLinha(posicao.getLinha() + direcao);
        p.setColuna(posicao.getColuna() + 1);
        if (getTabuleiro().posicaoValida(p) && getTabuleiro().temPecaAdversaria(p, this)) {
            matrizMovimentos[p.getLinha()][p.getColuna()] = true;
        }

        // En Passant
        if (getTabuleiro().getPeaoVulneravelEnPassant() != null) {
            if (posicao.getLinha() == (getCor() == Cor.BRANCA ? 3 : 4)) {
                Posicao posPeaoVulneravel = getTabuleiro().getPeaoVulneravelEnPassant().getPosicao();
                if (posPeaoVulneravel.getColuna() == posicao.getColuna() - 1) {
                    matrizMovimentos[posPeaoVulneravel.getLinha() + direcao][posPeaoVulneravel.getColuna()] = true;
                }
                if (posPeaoVulneravel.getColuna() == posicao.getColuna() + 1) {
                    matrizMovimentos[posPeaoVulneravel.getLinha() + direcao][posPeaoVulneravel.getColuna()] = true;
                }
            }
        }


        return matrizMovimentos;
    }
}
