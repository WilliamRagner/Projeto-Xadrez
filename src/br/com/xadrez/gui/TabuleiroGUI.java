package br.com.xadrez.gui;

import br.com.xadrez.modelo.Bispo;
import br.com.xadrez.modelo.Cavalo;
import br.com.xadrez.modelo.Cor;
import br.com.xadrez.modelo.Peca;
import br.com.xadrez.modelo.Posicao;
import br.com.xadrez.modelo.Rainha;
import br.com.xadrez.modelo.Tabuleiro;
import br.com.xadrez.modelo.Torre;
import javax.swing.*;
import java.awt.*;

public class TabuleiroGUI extends JFrame {

    private final JPanel painelTabuleiro;
    private final JButton[][] casas = new JButton[8][8];
    private Tabuleiro tabuleiro;
    private Peca pecaSelecionada;
    private boolean[][] movimentosPossiveis;
    private Posicao pecaNaPosicao;


    public TabuleiroGUI() {
        setTitle("Jogo de Xadrez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        tabuleiro = new Tabuleiro();
        painelTabuleiro = new JPanel(new GridLayout(8, 8));
        painelTabuleiro.setPreferredSize(new Dimension(800, 800));
        
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

        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Jogo");
        JMenuItem reiniciar = new JMenuItem("Começar outra partida");
        reiniciar.addActionListener(e -> reiniciarJogo());
        JMenuItem sair = new JMenuItem("Sair do jogo");
        sair.addActionListener(e -> System.exit(0));
        menu.add(reiniciar);
        menu.add(sair);
        menuBar.add(menu);
        setJMenuBar(menuBar);

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

                if (tabuleiro.getPromocao() != null) {
                    promoverPeao();
                }

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

    private void promoverPeao() {
        Object[] options = {"Rainha", "Torre", "Bispo", "Cavalo"};
        int n = JOptionPane.showOptionDialog(this,
                "Escolha a peça para promover o peão:",
                "Promoção de Peão",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        Peca novaPeca = null;
        Cor cor = tabuleiro.getPromocao().getCor();
        switch (n) {
            case 0:
                novaPeca = new Rainha(cor);
                break;
            case 1:
                novaPeca = new Torre(cor);
                break;
            case 2:
                novaPeca = new Bispo(cor);
                break;
            case 3:
                novaPeca = new Cavalo(cor);
                break;
            default:
                novaPeca = new Rainha(cor);
                break;
        }
        tabuleiro.promoverPeca(novaPeca);
        atualizarTabuleiro();
    }

     private void highlightMovimentos() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (movimentosPossiveis[i][j]) {
                    casas[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 3));
                }
            }
        }
    }

    private void limparHighlights() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                casas[i][j].setBorder(null);
            }
        }
    }


    private void reiniciarJogo() {
        tabuleiro = new Tabuleiro();
        pecaSelecionada = null;
        movimentosPossiveis = null;
        pecaNaPosicao = null;
        limparHighlights();
        atualizarTabuleiro();
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
