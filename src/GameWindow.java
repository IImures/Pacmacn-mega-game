import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private JFrame window;
    private Entity pacman;
    public GameWindow(JFrame window){
        this.window = window;
        window.setVisible(false);
//
//
//        JPanel panel = new JPanel();
//        panel.setPreferredSize(new Dimension(500, 500));
//        panel.setBorder(BorderFactory.createLineBorder(Color.RED)); // add a border to the panel to check its size and position
//        panel.setLayout(null);
//
//        pacman = new Entity(panel, 100, 100);
//        pacman.setBounds(pacman.getPosition().x, pacman.getPosition().y, 50, 50);
//        panel.addKeyListener(new MoveContol(pacman));
//
//        panel.add(pacman);
//        panel.setFocusable(true);
//
//        add(panel);
//
//        pack();
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setBorder(BorderFactory.createLineBorder(Color.RED));
        panel.setBackground(Color.black);

        pacman = new Entity(panel, 100, 100);
        panel.add(pacman);
        panel.setFocusable(true);
        panel.addKeyListener(new MoveContol(pacman));

        add(panel);

        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        repaint();
    }
}
