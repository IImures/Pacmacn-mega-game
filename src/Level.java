import javax.swing.*;
import java.awt.*;

public class Level extends JPanel {
    private int wight = 100;
    private int height = 100;
    private int BLOCK_SIZE = 16;

    private Entity pacman1;
    private Entity pacman2;
//    private JFrame window;
//
//    public Level(JFrame window){
//        this.window = window;
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setSize(new Dimension(wight * BLOCK_SIZE, height * BLOCK_SIZE));
//        panel.setBorder(BorderFactory.createLineBorder(Color.RED));
//        panel.setBackground(Color.black);
//        pacman1 = new Entity(panel, 50, 50);
//        pacman2 = new Entity(panel, 50, 50);
//        panel.add(pacman1);
//        panel.add(pacman2);
//        panel.setFocusable(true);
//        panel.addKeyListener(new MoveContol(pacman1));
//        setVisible(true);
//    }

    public Level(){

        setLayout(new BorderLayout());
        setSize(new Dimension(wight * BLOCK_SIZE, height * BLOCK_SIZE));
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setBackground(Color.black);

        pacman1 = new Entity(this, 50, 50, "Test1");
        pacman2 = new Entity(this, 100, 100, "Test2");
        pacman1.setBounds(pacman1.getPosition().x, pacman1.getPosition().y, 50, 50);
        pacman2.setBounds(pacman2.getPosition().x, pacman2.getPosition().y, 50, 50);

        add(pacman1);
        add(pacman2);

        setFocusable(true);
        addKeyListener(new MoveContol(pacman1));
        addKeyListener(new MoveContol(pacman2));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pacman1.getCharacter(), pacman1.getPosition().x, pacman1.getPosition().y, null);
//        g.drawImage(pacman2.getCharacter(), pacman2.getPosition().x, pacman2.getPosition().y, null);
    }

    public JPanel getPanel(){
        return this;
    }

}
