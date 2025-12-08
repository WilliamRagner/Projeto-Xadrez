package br.com.xadrez.gui;

import br.com.xadrez.modelo.Cor;
import br.com.xadrez.modelo.Peca;
import br.com.xadrez.modelo.Posicao;
import br.com.xadrez.modelo.Tabuleiro;
import javax.swing.*;
import java.awt.*;

public class TabuleiroGUI extends JFrame {

    private final JPanel painelTabuleiro;
    private final JButton[][] casas = new JButton[8][8];
    private final Tabuleiro tabuleiro;
    private Peca pecaSelecionada;
    private boolean[][] movimentosPossiveis;
    private Posicao pecaNaPosicao;


    public TabuleiroGUI() {
        setTitle("Jogo de Xadrez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        tabuleiro = new Tabuleiro();
        painelTabuleiro = new JPanel(new GridLayout(8, 8));
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int linha = i;
                final int coluna = j;
                casas[i][j] = new JButton();
                casas[i][j].setFont(new Font("Arial Unicode MS", Font.PLAIN, 40));
                
                if ((i + j) % 2 == 0) {
                    casas[i][j].setBackground(new Color(240, 217, 181)); // Cor clara
                } else {
                    casas[i][j].setBackground(new Color(181, 136, 99)); // Cor escura
                }
                
                casas[i][j].addActionListener(e -> onCasaClicked(linha, coluna));
                painelTabuleiro.add(casas[i][j]);
            }
        }

        add(painelTabuleiro, new GridBagConstraints());
        
        pack();
        setLocationRelativeTo(null); // Centralizar na tela
        
        atualizarTabuleiro();
        setVisible(true);
    }
     private void onCasaClicked(int linha, int coluna) {
        if (pecaSelecionada == null) {
            Peca peca = tabuleiro.getPeca(linha, coluna);
            if (peca != null && peca.getCor() == tabuleiro.getTurno()) {
                pecaSelecionada = peca;
                pecaNaPosicao = new Posicao(linha, coluna);
                movimentosPossiveis = peca.movimentosPossiveis();
                highlightMovimentos();
            }
        } else {
            if (movimentosPossiveis[linha][coluna]) {
                tabuleiro.moverPeca(pecaNaPosicao, new Posicao(linha, coluna));
                pecaSelecionada = null;
                movimentosPossiveis = null;
                pecaNaPosicao = null;
                atualizarTabuleiro();
                limparHighlights();

                if (tabuleiro.estaEmXequeMate(tabuleiro.getTurno())) {
                    JOptionPane.showMessageDialog(this, "Xeque-mate! As " + (tabuleiro.getTurno() == Cor.BRANCA ? "pretas" : "brancas") + " venceram!");
                }

            } else {
                pecaSelecionada = null;
                movimentosPossiveis = null;
                pecaNaPosicao = null;
                limparHighlights();
            }
        }
    }
     private void highlightMovimentos() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (movimentosPossiveis[i][j]) {
                    casas[i][j].setBackground(new Color(130, 151, 105));
                }
            }
        }
    }

    private void limparHighlights() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    casas[i][j].setBackground(new Color(240, 217, 181));
                } else {
                    casas[i][j].setBackground(new Color(181, 136, 99));
                }
            }
        }
    }


    private void atualizarTabuleiro() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca peca = tabuleiro.getPeca(i, j);
                if (peca != null) {
                    casas[i][j].setText(peca.getSimbolo());
                } else {
                    casas[i][j].setText("");
                }
            }
        }
    }
}
