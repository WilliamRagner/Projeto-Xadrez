package br.com.xadrez.modelo;

public class Tabuleiro {

    private final Peca[][] casas = new Peca[8][8];
    private Cor turno;
    private java.util.List<Peca> pecasCapturadas;


    public Tabuleiro() {
        this.turno = Cor.BRANCA;
        this.pecasCapturadas = new java.util.ArrayList<>();
        iniciarPecas();
    }
     public Cor getTurno() {
        return turno;
    }

    private void proximoTurno() {
        turno = (turno == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
    }


    public Tabuleiro(boolean iniciarPecas) {
        if (iniciarPecas) {
            iniciarPecas();
        }
    }

    public void iniciarPecas() {
        // Peças Pretas
        setPeca(new Torre(Cor.PRETA), new Posicao(0, 0));
        setPeca(new Cavalo(Cor.PRETA), new Posicao(0, 1));
        setPeca(new Bispo(Cor.PRETA), new Posicao(0, 2));
        setPeca(new Rainha(Cor.PRETA), new Posicao(0, 3));
        setPeca(new Rei(Cor.PRETA), new Posicao(0, 4));
        setPeca(new Bispo(Cor.PRETA), new Posicao(0, 5));
        setPeca(new Cavalo(Cor.PRETA), new Posicao(0, 6));
        setPeca(new Torre(Cor.PRETA), new Posicao(0, 7));
        for (int i = 0; i < 8; i++) {
            setPeca(new Peao(Cor.PRETA), new Posicao(1, i));
        }

        // Peças Brancas
        setPeca(new Torre(Cor.BRANCA), new Posicao(7, 0));
        setPeca(new Cavalo(Cor.BRANCA), new Posicao(7, 1));
        setPeca(new Bispo(Cor.BRANCA), new Posicao(7, 2));
        setPeca(new Rainha(Cor.BRANCA), new Posicao(7, 3));
        setPeca(new Rei(Cor.BRANCA), new Posicao(7, 4));
        setPeca(new Bispo(Cor.BRANCA), new Posicao(7, 5));
        setPeca(new Cavalo(Cor.BRANCA), new Posicao(7, 6));
        setPeca(new Torre(Cor.BRANCA), new Posicao(7, 7));
        for (int i = 0; i < 8; i++) {
            setPeca(new Peao(Cor.BRANCA), new Posicao(6, i));
        }
    }

    public void moverPeca(Posicao da, Posicao para) {
        Peca peca = getPeca(da);
        if (peca.movimentosPossiveis()[para.getLinha()][para.getColuna()]) {
            
            Peca pecaCapturada = fazerMovimentoTemporario(da, para);
            boolean emXeque = estaEmXeque(peca.getCor());
            desfazerMovimento(da, para, pecaCapturada);

            if (emXeque) {
                return;
            }

            pecaCapturada = getPeca(para);
            if (pecaCapturada != null) {
                pecasCapturadas.add(pecaCapturada);
            }
            casas[da.getLinha()][da.getColuna()] = null;
            casas[para.getLinha()][para.getColuna()] = peca;
            peca.setPosicao(para);
            if (peca instanceof Peao) {
                ((Peao) peca).incrementaMovimentos();
            }
            proximoTurno();
        }
    }

    public void setPeca(Peca p, Posicao pos) {
        casas[pos.getLinha()][pos.getColuna()] = p;
        p.setPosicao(pos);
        p.setTabuleiro(this);
    }

    public Peca getPeca(int linha, int coluna) {
        if (linha < 0 || linha >= 8 || coluna < 0 || coluna >= 8) {
            return null; // Fora do tabuleiro
        }
        return casas[linha][coluna];
    }

    public Peca getPeca(Posicao p) {
        if (!posicaoValida(p)) {
            return null;
        }
        return casas[p.getLinha()][p.getColuna()];
    }

    public int getLinhas() {
        return casas.length;
    }

    public int getColunas() {
        return casas[0].length;
    }

    public boolean posicaoValida(Posicao p) {
        return p.getLinha() >= 0 && p.getLinha() < getLinhas() &&
                p.getColuna() >= 0 && p.getColuna() < getColunas();
    }

    public boolean temPeca(Posicao p) {
        return getPeca(p) != null;
    }

    public boolean temPecaAdversaria(Posicao p, Peca peca) {
        Peca p2 = getPeca(p);
        return p2 != null && p2.getCor() != peca.getCor();
    }

    private Rei reiEm(Cor cor) {
        for (int i = 0; i < getLinhas(); i++) {
            for (int j = 0; j < getColunas(); j++) {
                Peca p = getPeca(i, j);
                if (p instanceof Rei && p.getCor() == cor) {
                    return (Rei) p;
                }
            }
        }
        return null;
    }

    public java.util.List<Peca> getPecas(Cor cor) {
        java.util.List<Peca> pecas = new java.util.ArrayList<>();
        for (int i = 0; i < getLinhas(); i++) {
            for (int j = 0; j < getColunas(); j++) {
                Peca p = getPeca(i, j);
                if (p != null && p.getCor() == cor) {
                    pecas.add(p);
                }
            }
        }
        return pecas;
    }

    public boolean estaEmXeque(Cor cor) {
        Rei rei = reiEm(cor);
        if (rei == null) {
            return false;
        }
        Posicao posRei = rei.getPosicao();
        Cor corOponente = (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
        for (Peca p : getPecas(corOponente)) {
            if (p.movimentosPossiveis()[posRei.getLinha()][posRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }
    private Peca fazerMovimentoTemporario(Posicao da, Posicao para) {
        Peca peca = getPeca(da);
        Peca pecaCapturada = getPeca(para);
        casas[da.getLinha()][da.getColuna()] = null;
        casas[para.getLinha()][para.getColuna()] = peca;
        peca.setPosicao(para);
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao da, Posicao para, Peca pecaCapturada) {
        Peca peca = getPeca(para);
        casas[da.getLinha()][da.getColuna()] = peca;
        casas[para.getLinha()][para.getColuna()] = pecaCapturada;
        peca.setPosicao(da);
    }


    public boolean estaEmXequeMate(Cor cor) {
        if (!estaEmXeque(cor)) {
            return false;
        }

        for (Peca p : getPecas(cor)) {
            boolean[][] movimentos = p.movimentosPossiveis();
            for (int i = 0; i < getLinhas(); i++) {
                for (int j = 0; j < getColunas(); j++) {
                    if (movimentos[i][j]) {
                        Posicao da = p.getPosicao();
                        Posicao para = new Posicao(i, j);
                        Peca pecaCapturada = fazerMovimentoTemporario(da, para);
                        boolean aindaEmXeque = estaEmXeque(cor);
                        desfazerMovimento(da, para, pecaCapturada);
                        if (!aindaEmXeque) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
