import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Launcher {

    private static void launchTerminal(JFrame frame) {
        frame.dispose();
        JogoGalo.main(new String[] {});
    }

    private static void launchGui(JFrame frame) {
        frame.dispose();
        JogoGaloGui.main(new String[] {});
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
            // se falhar, fica com o look & feel default
        }
        JFrame launcher = new JFrame("Jogo do Galo");
        launcher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launcher.setSize(500, 250);
        launcher.setResizable(false);
        launcher.setBackground(Color.WHITE);

        JPanel topo = new JPanel();
        JLabel label = new JLabel("Escolhe como queres jogar:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        topo.add(label);

        JButton terminal = new JButton("Jogar no Terminal");
        terminal.addActionListener(e -> launchTerminal(launcher));
        JButton gui = new JButton("Jogar via GUI");
        gui.addActionListener(e -> launchGui(launcher));
        terminal.setFocusable(false); //tirar aquele quadrado feio que aparece a volta do texto
        gui.setFocusable(false);

        JPanel centro = new JPanel();
        centro.setFont(new Font("SansSerif", Font.PLAIN, 100));
        centro.setLayout(new FlowLayout());
        centro.add(terminal);
        centro.add(gui);

        launcher.add(centro, BorderLayout.CENTER);
        launcher.add(topo, BorderLayout.NORTH);
        launcher.pack();
        launcher.setVisible(true);
    }
}
