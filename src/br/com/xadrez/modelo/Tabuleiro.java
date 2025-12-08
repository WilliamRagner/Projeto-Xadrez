package br.com.xadrez.modelo;

public class Tabuleiro {

    private final Peca[][] casas = new Peca[8][8];

    public Tabuleiro() {
        iniciarPecas();
    }

    private void iniciarPecas() {
        // Peças Pretas
        casas[0][0] = new Torre(Cor.PRETA);
        casas[0][1] = new Cavalo(Cor.PRETA);
        casas[0][2] = new Bispo(Cor.PRETA);
        casas[0][3] = new Rainha(Cor.PRETA);
        casas[0][4] = new Rei(Cor.PRETA);
        casas[0][5] = new Bispo(Cor.PRETA);
        casas[0][6] = new Cavalo(Cor.PRETA);
        casas[0][7] = new Torre(Cor.PRETA);
        for (int i = 0; i < 8; i++) {
            casas[1][i] = new Peao(Cor.PRETA);
        }

        // Peças Brancas
        casas[7][0] = new Torre(Cor.BRANCA);
        casas[7][1] = new Cavalo(Cor.BRANCA);
        casas[7][2] = new Bispo(Cor.BRANCA);
        casas[7][3] = new Rainha(Cor.BRANCA);
        casas[7][4] = new Rei(Cor.BRANCA);
        casas[7][5] = new Bispo(Cor.BRANCA);
        casas[7][6] = new Cavalo(Cor.BRANCA);
        casas[7][7] = new Torre(Cor.BRANCA);
        for (int i = 0; i < 8; i++) {
            casas[6][i] = new Peao(Cor.BRANCA);
        }
    }

    public Peca getPeca(int linha, int coluna) {
        if (linha < 0 || linha >= 8 || coluna < 0 || coluna >= 8) {
            return null; // Fora do tabuleiro
        }
        return casas[linha][coluna];
    }
}
