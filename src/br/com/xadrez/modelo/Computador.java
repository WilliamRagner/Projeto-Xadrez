package br.com.xadrez.modelo;

import java.util.ArrayList;
import java.util.List;

public class Computador {

    public static Jogada escolherMelhorJogada(Tabuleiro tabuleiro, int profundidade) {
        Jogada melhorJogada = null;
        Cor corComputador = tabuleiro.getTurno();
        boolean isMaximizando = (corComputador == Cor.PRETA);
        int melhorPontuacao = isMaximizando ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        List<Jogada> jogadasPossiveis = gerarJogadasPossiveis(tabuleiro, corComputador);

        for (Jogada jogada : jogadasPossiveis) {
            Peca pecaCapturada = tabuleiro.fazerMovimentoTemporario(jogada.getDe(), jogada.getPara());
            int pontuacao = minimax(tabuleiro, profundidade - 1, !isMaximizando);
            tabuleiro.desfazerMovimento(jogada.getDe(), jogada.getPara(), pecaCapturada);

            if (isMaximizando) {
                if (pontuacao > melhorPontuacao) {
                    melhorPontuacao = pontuacao;
                    melhorJogada = jogada;
                }
            } else {
                if (pontuacao < melhorPontuacao) {
                    melhorPontuacao = pontuacao;
                    melhorJogada = jogada;
                }
            }
        }
        return melhorJogada;
    }

    private static int minimax(Tabuleiro tabuleiro, int profundidade, boolean isMaximizando) {
        if (profundidade == 0 || tabuleiro.estaEmXequeMate(Cor.BRANCA) || tabuleiro.estaEmXequeMate(Cor.PRETA)) {
            return avaliarTabuleiro(tabuleiro);
        }

        Cor corAtual = tabuleiro.getTurno();
        List<Jogada> jogadasPossiveis = gerarJogadasPossiveis(tabuleiro, corAtual);

        if (isMaximizando) {
            int melhorPontuacao = Integer.MIN_VALUE;
            for (Jogada jogada : jogadasPossiveis) {
                Peca pecaCapturada = tabuleiro.fazerMovimentoTemporario(jogada.getDe(), jogada.getPara());
                melhorPontuacao = Math.max(melhorPontuacao, minimax(tabuleiro, profundidade - 1, false));
                tabuleiro.desfazerMovimento(jogada.getDe(), jogada.getPara(), pecaCapturada);
            }
            return melhorPontuacao;
        } else {
            int melhorPontuacao = Integer.MAX_VALUE;
            for (Jogada jogada : jogadasPossiveis) {
                Peca pecaCapturada = tabuleiro.fazerMovimentoTemporario(jogada.getDe(), jogada.getPara());
                melhorPontuacao = Math.min(melhorPontuacao, minimax(tabuleiro, profundidade - 1, true));
                tabuleiro.desfazerMovimento(jogada.getDe(), jogada.getPara(), pecaCapturada);
            }
            return melhorPontuacao;
        }
    }

    private static List<Jogada> gerarJogadasPossiveis(Tabuleiro tabuleiro, Cor cor) {
        List<Jogada> jogadasPossiveis = new ArrayList<>();
        List<Peca> pecas = tabuleiro.getPecas(cor);

        for (Peca peca : pecas) {
            boolean[][] movimentos = peca.movimentosPossiveis();
            for (int i = 0; i < movimentos.length; i++) {
                for (int j = 0; j < movimentos[i].length; j++) {
                    if (movimentos[i][j]) {
                        Posicao de = peca.getPosicao();
                        Posicao para = new Posicao(i, j);

                        Peca pecaCapturada = tabuleiro.fazerMovimentoTemporario(de, para);
                        boolean emXeque = tabuleiro.estaEmXeque(cor);
                        tabuleiro.desfazerMovimento(de, para, pecaCapturada);

                        if (!emXeque) {
                            jogadasPossiveis.add(new Jogada(de, para));
                        }
                    }
                }
            }
        }
        return jogadasPossiveis;
    }

    public static int avaliarTabuleiro(Tabuleiro tabuleiro) {
        int pontuacao = 0;
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                Peca peca = tabuleiro.getPeca(i, j);
                if (peca != null) {
                    if (peca.getCor() == Cor.PRETA) {
                        pontuacao += peca.getValor();
                    } else {
                        pontuacao -= peca.getValor();
                    }
                }
            }
        }
        return pontuacao;
    }
}
