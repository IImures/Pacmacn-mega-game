import javax.swing.*;
import java.awt.*;


public class GameWindow extends JFrame {
    private JFrame window;
    public GameWindow(JFrame window){
        this.window = window;
        window.setVisible(false);

//        JPanel panel = new JPanel(new BorderLayout());
//        setLayout(null);
//        panel.setSize(new Dimension(500, 500));
//        panel.setBorder(BorderFactory.createLineBorder(Color.RED));
//        panel.setBackground(Color.black);
//
//
//        Entity pacman1 = new Entity(panel, 100, 100);
//        Entity pacman2 = new Entity(panel, 200, 200);
//        pacman1.setBounds(pacman1.getPosition().x, pacman1.getPosition().y, 50, 50);
//        pacman2.setBounds(pacman2.getPosition().x, pacman2.getPosition().y, 50, 50);
//
//        panel.add(pacman1, BorderLayout.PAGE_START);
//        panel.add(pacman2, BorderLayout.CENTER);
//
//        panel.setFocusable(true);
//        panel.addKeyListener(new MoveContol(pacman2));
//
//        add(panel);
        setLayout(new BorderLayout());
        Level level = new Level();

        add(level, BorderLayout.CENTER);

        pack();
        //setSize(500, 500);
        setMinimumSize(new Dimension(100,100));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
