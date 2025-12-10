package br.com.xadrez.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa o tabuleiro do jogo de xadrez, gerenciando as peças,
 * as regras de movimento e o estado do jogo.
 */
public class Tabuleiro {

    private static final int LINHAS = 8;
    private static final int COLUNAS = 8;

    private final Peca[][] casas = new Peca[LINHAS][COLUNAS];
    private Cor turno;
    private List<Peca> pecasCapturadas;
    private Peao peaoVulneravelEnPassant;
    private Peca promocao;

    public Tabuleiro() {
        this.turno = Cor.BRANCA;
        this.pecasCapturadas = new ArrayList<>();
        iniciarPecas();
    }

    public Cor getTurno() {
        return turno;
    }

    public Peca getPromocao() {
        return promocao;
    }

    public Peao getPeaoVulneravelEnPassant() {
        return peaoVulneravelEnPassant;
    }

    public void promoverPeca(Peca peca) {
        casas[promocao.getPosicao().getLinha()][promocao.getPosicao().getColuna()] = peca;
        peca.setPosicao(promocao.getPosicao());
        peca.setTabuleiro(this);
        promocao = null;
        proximoTurno();
    }

    private void proximoTurno() {
        turno = (turno == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
        peaoVulneravelEnPassant = null;
    }

    public void iniciarPecas() {
        // Peças Pretas
        int linhaPretas = 0;
        int linhaPeoesPretos = 1;
        setPeca(new Torre(Cor.PRETA), new Posicao(linhaPretas, 0));
        setPeca(new Cavalo(Cor.PRETA), new Posicao(linhaPretas, 1));
        setPeca(new Bispo(Cor.PRETA), new Posicao(linhaPretas, 2));
        setPeca(new Rainha(Cor.PRETA), new Posicao(linhaPretas, 3));
        setPeca(new Rei(Cor.PRETA), new Posicao(linhaPretas, 4));
        setPeca(new Bispo(Cor.PRETA), new Posicao(linhaPretas, 5));
        setPeca(new Cavalo(Cor.PRETA), new Posicao(linhaPretas, 6));
        setPeca(new Torre(Cor.PRETA), new Posicao(linhaPretas, 7));
        for (int i = 0; i < COLUNAS; i++) {
            setPeca(new Peao(Cor.PRETA), new Posicao(linhaPeoesPretos, i));
        }

        // Peças Brancas
        int linhaBrancas = LINHAS - 1;
        int linhaPeoesBrancos = LINHAS - 2;
        setPeca(new Torre(Cor.BRANCA), new Posicao(linhaBrancas, 0));
        setPeca(new Cavalo(Cor.BRANCA), new Posicao(linhaBrancas, 1));
        setPeca(new Bispo(Cor.BRANCA), new Posicao(linhaBrancas, 2));
        setPeca(new Rainha(Cor.BRANCA), new Posicao(linhaBrancas, 3));
        setPeca(new Rei(Cor.BRANCA), new Posicao(linhaBrancas, 4));
        setPeca(new Bispo(Cor.BRANCA), new Posicao(linhaBrancas, 5));
        setPeca(new Cavalo(Cor.BRANCA), new Posicao(linhaBrancas, 6));
        setPeca(new Torre(Cor.BRANCA), new Posicao(linhaBrancas, 7));
        for (int i = 0; i < COLUNAS; i++) {
            setPeca(new Peao(Cor.BRANCA), new Posicao(linhaPeoesBrancos, i));
        }
    }

    public void moverPeca(Posicao da, Posicao para) {
        Peca peca = getPeca(da);
        if (peca == null || !peca.movimentosPossiveis()[para.getLinha()][para.getColuna()]) {
            return;
        }

        // Verifica se o movimento coloca o próprio rei em xeque
        Peca pecaCapturadaTemporaria = fazerMovimentoTemporario(da, para);
        boolean emXeque = estaEmXeque(peca.getCor());
        desfazerMovimento(da, para, pecaCapturadaTemporaria);

        if (emXeque) {
            return;
        }

        // Executa o movimento
        Peca pecaCapturada = getPeca(para);
        if (pecaCapturada != null) {
            pecasCapturadas.add(pecaCapturada);
        }

        // Trata captura En Passant (que é um caso especial de captura)
        if (peca instanceof Peao && da.getColuna() != para.getColuna() && pecaCapturada == null) {
            pecaCapturada = capturarEnPassant(peca, para);
            pecasCapturadas.add(pecaCapturada);
        }

        casas[da.getLinha()][da.getColuna()] = null;
        casas[para.getLinha()][para.getColuna()] = peca;
        peca.setPosicao(para);

        tratarMovimentosEspeciaisEAtualizarEstado(peca, da, para);

        // Se uma promoção está pendente, o turno não avança até que a peça seja escolhida.
        if (promocao != null) {
            return;
        }

        proximoTurno();
    }

    private Peca capturarEnPassant(Peca peao, Posicao para) {
        int direcao = peao.getCor() == Cor.BRANCA ? 1 : -1;
        Posicao posPeaoCapturado = new Posicao(para.getLinha() + direcao, para.getColuna());
        Peca pecaCapturada = getPeca(posPeaoCapturado);
        casas[posPeaoCapturado.getLinha()][posPeaoCapturado.getColuna()] = null;
        return pecaCapturada;
    }

    private void tratarMovimentosEspeciaisEAtualizarEstado(Peca peca, Posicao da, Posicao para) {
        if (peca instanceof Peao) {
            ((Peao) peca).incrementaMovimentos();
            // Define vulnerabilidade En Passant
            if (Math.abs(da.getLinha() - para.getLinha()) == 2) {
                peaoVulneravelEnPassant = (Peao) peca;
            } else {
                peaoVulneravelEnPassant = null;
            }
            // Define estado de promoção
            if (para.getLinha() == 0 || para.getLinha() == LINHAS - 1) {
                promocao = peca;
            }
        } else {
            peaoVulneravelEnPassant = null;
        }

        if (peca instanceof Rei) {
            ((Rei) peca).incrementaMovimentos();
            if (Math.abs(da.getColuna() - para.getColuna()) == 2) {
                tratarRoque(da, para);
            }
        }

        if (peca instanceof Torre) {
            ((Torre) peca).incrementaMovimentos();
        }

        if (!(peca instanceof Peao)) {
             promocao = null;
        }
    }

    private void tratarRoque(Posicao reiDe, Posicao reiPara) {
        // Roque pequeno
        if (reiPara.getColuna() > reiDe.getColuna()) {
            Posicao torreDe = new Posicao(reiDe.getLinha(), COLUNAS - 1);
            Posicao torrePara = new Posicao(reiDe.getLinha(), reiPara.getColuna() - 1);
            Peca torre = getPeca(torreDe);
            casas[torreDe.getLinha()][torreDe.getColuna()] = null;
            casas[torrePara.getLinha()][torrePara.getColuna()] = torre;
            torre.setPosicao(torrePara);
        }
        // Roque grande
        else {
            Posicao torreDe = new Posicao(reiDe.getLinha(), 0);
            Posicao torrePara = new Posicao(reiDe.getLinha(), reiPara.getColuna() + 1);
            Peca torre = getPeca(torreDe);
            casas[torreDe.getLinha()][torreDe.getColuna()] = null;
            casas[torrePara.getLinha()][torrePara.getColuna()] = torre;
            torre.setPosicao(torrePara);
        }
    }

    public void setPeca(Peca p, Posicao pos) {
        casas[pos.getLinha()][pos.getColuna()] = p;
        p.setPosicao(pos);
        p.setTabuleiro(this);
    }

    public Peca getPeca(int linha, int coluna) {
        if (linha < 0 || linha >= LINHAS || coluna < 0 || coluna >= COLUNAS) {
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

    public List<Peca> getPecas(Cor cor) {
        List<Peca> pecas = new ArrayList<>();
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
    public Peca fazerMovimentoTemporario(Posicao da, Posicao para) {
        Peca peca = getPeca(da);
        Peca pecaCapturada = getPeca(para);
        casas[da.getLinha()][da.getColuna()] = null;
        casas[para.getLinha()][para.getColuna()] = peca;
        peca.setPosicao(para);
        return pecaCapturada;
    }

    public void desfazerMovimento(Posicao da, Posicao para, Peca pecaCapturada) {
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
