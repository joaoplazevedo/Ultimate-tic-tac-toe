import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

public class JogoGaloGui {
    private static JButton[][][] botoes = new JButton[9][3][3];
    private static int forcedPlay = -1;
    private static char player = 'O';
    private static UltimateTabuleiro t = new UltimateTabuleiro();
    private static JFrame frame;

    private static boolean isValidClick(int board, int i, int j) {
        if (forcedPlay != -1 && forcedPlay != (board + 1))
            return false;

        Tabuleiro tb = t.getBoard(board + 1);
        if (tb.checkEnd() != '-')
            return false;

        if (!tb.validPlay(i, j))
            return false;

        return true;
    }

    private static void restart() {
        frame.dispose();
        main(new String[] {});
    }

    private static void unlight() {
        for (int b = 0; b < 9; b++) {
            Tabuleiro tb = t.getBoard(b + 1);
            char winner = tb.checkEnd();
            Color cor = null;
            if (winner == 'O')
                cor = Color.BLUE;
            else if (winner == 'X')
                cor = Color.RED;
            else if (winner == 'D')
                cor = Color.darkGray;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (winner != '-') {
                        botoes[b][i][j].setBackground(cor);
                        botoes[b][i][j].setForeground(cor);
                    } else {
                        botoes[b][i][j].setBackground(Color.GRAY);
                    }
                }
            }
        }
    }

    private static void highlight() {
        if (forcedPlay == -1)
            return;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[forcedPlay - 1][i][j].setBackground(Color.YELLOW);
            }
        }
    }

    private static void click(JLabel textoTopo, JButton restart, int b, int i, int j) {
        if (!isValidClick(b, i, j))
            return;
        forcedPlay = t.play(player, b + 1, i, j);
        botoes[b][i][j].setText((player == 'O') ? "O" : "X");
        botoes[b][i][j].setForeground((player == 'O') ? Color.BLUE : Color.RED);
        player = (player == 'O') ? 'X' : 'O';
        unlight();
        if (JogoGalo.checkWinOrDraw(t)) {
            disableBoard();
            char winner = t.checkUltimateEnd();
            if (winner == 'D') {
                textoTopo.setText("Empate!!!!!!!!!!!!!!");
            } else if (winner == 'X') {
                textoTopo.setText("'X' Ganhou!!!");
            } else if (winner == 'O') {
                textoTopo.setText("'O' Ganhou!!!");
            }
            return;
        }
        textoTopo.setForeground((player == 'O') ? Color.BLUE : Color.RED);
        textoTopo.setText("Turno do " + ((player == 'O') ? "O" : "X"));
        highlight();
    }

    private static void disableBoard() {
        for (int b = 0; b < 9; b++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    botoes[b][i][j].setEnabled(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // fica com o look & feel default
        }

        frame = new JFrame("Jogo do Galo!!");
        frame.setSize(800, 850);
        frame.setLocationRelativeTo(null); // centrado
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ImageIcon imageIcon = new ImageIcon("restart.png"); // load the image to a
        // imageIcon
        // Image image = imageIcon.getImage(); // transform it
        // Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        // // scale it the smooth way
        // imageIcon = new ImageIcon(newimg); // transform it back
        JButton restart = new JButton("\u27F3");
        restart.setFont(new Font("Arial", Font.BOLD, 35));
        restart.setFocusable(false);
        restart.addActionListener(e -> restart());

        JLabel textoTopo = new JLabel("Jogo do Galo");
        JLabel bonito = new JLabel();
        bonito.setPreferredSize(restart.getPreferredSize());
        // topo.setBackground(Color.GRAY);
        // topo.setOpaque(true);
        textoTopo.setFont(new Font("Arial", Font.BOLD, 50));
        textoTopo.setHorizontalAlignment(JLabel.CENTER);
        JPanel topo = new JPanel(new BorderLayout()); // nao precisam de ser do msm tamanho os componentes
        topo.add(bonito, BorderLayout.WEST);
        topo.add(textoTopo);
        topo.add(restart, BorderLayout.EAST);
        frame.add(topo, BorderLayout.NORTH);

        JPanel start = new JPanel(new GridBagLayout());
        JButton play = new JButton("Jogar");
        play.setFocusable(false);
        play.setFont(new Font("Arial", Font.BOLD, 50));

        JPanel centro = new JPanel();
        centro.setLayout(new GridLayout(9, 9));
        play.addActionListener(e -> vaiCentro(start, centro));
        start.add(play);
        frame.add(start);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int b = (row / 3) * 3 + (col / 3);
                int i = row % 3;
                int j = col % 3;
                final int bb = b;
                final int ii = i;
                final int jj = j;
                botoes[b][i][j] = new JButton();
                botoes[b][i][j].addActionListener(e -> click(textoTopo, restart, bb, ii, jj));
                botoes[b][i][j].setFont(new Font("Arial", Font.BOLD, 65));
                botoes[b][i][j].setFocusable(false);
                botoes[b][i][j].setBackground(Color.gray);
                centro.add(botoes[b][i][j]);
                frame.setVisible(true);
            }
        }
    }

    private static void vaiCentro(JPanel start, JPanel centro) {
        frame.remove(start);
        frame.add(centro);
        frame.revalidate();
        frame.repaint();
    }
}
