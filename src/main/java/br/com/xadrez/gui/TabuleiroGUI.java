package br.com.xadrez.gui;

import br.com.xadrez.modelo.Bispo;
import br.com.xadrez.modelo.Cavalo;
import br.com.xadrez.modelo.Computador;
import br.com.xadrez.modelo.Cor;
import br.com.xadrez.modelo.Jogada;
import br.com.xadrez.modelo.Peca;
import br.com.xadrez.modelo.Posicao;
import br.com.xadrez.modelo.Rainha;
import br.com.xadrez.modelo.Tabuleiro;
import br.com.xadrez.modelo.Torre;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;

public class TabuleiroGUI extends JFrame {

    private final JPanel painelTabuleiro;
    private final JButton[][] casas = new JButton[8][8];
    private Tabuleiro tabuleiro;
    private Peca pecaSelecionada;
    private boolean[][] movimentosPossiveis;
    private Posicao pecaNaPosicao;


    private boolean contraComputador = false;
    private Cor corDoJogador = Cor.BRANCA;
    private Cor corDoComputador = Cor.PRETA;


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
        reiniciar.addActionListener(e -> reiniciarJogo(false));
        JMenuItem jogarContraComputador = new JMenuItem("Jogar contra Computador");
        jogarContraComputador.addActionListener(e -> reiniciarJogo(true));
        JMenuItem sair = new JMenuItem("Sair do jogo");
        sair.addActionListener(e -> System.exit(0));
        menu.add(reiniciar);
        menu.add(jogarContraComputador);
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
        if (contraComputador && tabuleiro.getTurno() == corDoComputador) {
            return; // Não é a vez do jogador
        }

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
                playSound();
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
                } else {
                    if (contraComputador) {
                        fazerJogadaComputador();
                    }
                }

            } else {
                pecaSelecionada = null;
                movimentosPossiveis = null;
                pecaNaPosicao = null;
                limparHighlights();
            }
        }
    }
    
    private void playSound() {
        try (InputStream inputStream = getClass().getResourceAsStream("/Sfx/public_sound_lisp_Confirmation.wav")) {
            if (inputStream == null) {
                System.err.println("Recurso de áudio não encontrado: /Sfx/public_sound_lisp_Confirmation.wav");
                return;
            }
            
            InputStream bufferedIn = new java.io.BufferedInputStream(inputStream);
            
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)) {
                Clip clip = AudioSystem.getClip();
                
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
                
                clip.open(audioIn);
                clip.start();
            }
        } catch (Exception ex) {
            // Não impede o jogo de continuar se o som falhar
            System.err.println("Erro ao tocar o som: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void fazerJogadaComputador() {
        if (contraComputador && tabuleiro.getTurno() == corDoComputador) {
            new SwingWorker<Jogada, Void>() {
                @Override
                protected Jogada doInBackground() throws Exception {
                    // Adiciona um pequeno atraso para o jogador perceber a jogada
                    Thread.sleep(500);
                    return Computador.escolherMelhorJogada(tabuleiro, 2);
                }

                @Override
                protected void done() {
                    try {
                        Jogada jogada = get();
                        if (jogada != null) {
                            tabuleiro.moverPeca(jogada.getDe(), jogada.getPara());
                            playSound();
                            atualizarTabuleiro();
                            if (tabuleiro.estaEmXequeMate(tabuleiro.getTurno())) {
                                JOptionPane.showMessageDialog(TabuleiroGUI.this, "Xeque-mate! As " + (tabuleiro.getTurno() == Cor.BRANCA ? "pretas" : "brancas") + " venceram!");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
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


    private void reiniciarJogo(boolean contraComputador) {
        this.contraComputador = contraComputador;
        tabuleiro = new Tabuleiro();
        pecaSelecionada = null;
        movimentosPossiveis = null;
        pecaNaPosicao = null;
        limparHighlights();

        if (contraComputador) {
            Object[] options = {"Brancas", "Pretas"};
            int n = JOptionPane.showOptionDialog(this,
                    "Escolha sua cor:",
                    "Escolha de Cor",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            corDoJogador = (n == JOptionPane.YES_OPTION) ? Cor.BRANCA : Cor.PRETA;
            corDoComputador = (corDoJogador == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;

            atualizarTabuleiro();
            if (corDoJogador == Cor.PRETA) {
                fazerJogadaComputador();
            }
        } else {
            atualizarTabuleiro();
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