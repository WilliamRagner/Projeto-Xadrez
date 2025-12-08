package br.com.xadrez.gui;

import br.com.xadrez.modelo.Peca;
import br.com.xadrez.modelo.Tabuleiro;
import javax.swing.*;
import java.awt.*;

public class TabuleiroGUI extends JFrame {

    private final JPanel painelTabuleiro;
    private final JButton[][] casas = new JButton[8][8];
    private final Tabuleiro tabuleiro;

    public TabuleiroGUI() {
        setTitle("Jogo de Xadrez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        tabuleiro = new Tabuleiro();
        painelTabuleiro = new JPanel(new GridLayout(8, 8));
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                casas[i][j] = new JButton();
                casas[i][j].setFont(new Font("Arial Unicode MS", Font.PLAIN, 40));
                
                if ((i + j) % 2 == 0) {
                    casas[i][j].setBackground(new Color(240, 217, 181)); // Cor clara
                } else {
                    casas[i][j].setBackground(new Color(181, 136, 99)); // Cor escura
                }
                
                painelTabuleiro.add(casas[i][j]);
            }
        }

        add(painelTabuleiro, new GridBagConstraints());
        
        pack();
        setLocationRelativeTo(null); // Centralizar na tela
        
        atualizarTabuleiro();
        setVisible(true);
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
