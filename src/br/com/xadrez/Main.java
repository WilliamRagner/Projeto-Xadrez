package br.com.xadrez;

import br.com.xadrez.gui.TabuleiroGUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TabuleiroGUI());
    }
}
