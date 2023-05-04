import javax.swing.*;
import java.awt.*;

public class Level extends JPanel {
    private int wight = 100;
    private int height = 100;
    private int BLOCK_SIZE = 16;

    private Pacman pacman;
    private Ghost[] ghosts;
    private int lives;
    private JPanel livesPanel;

    private JPanel board;
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
        makeLives();

        board = new JPanel();
        board.setSize(new Dimension(wight * BLOCK_SIZE, height * BLOCK_SIZE));

        ghosts = new Ghost[4];
        pacman = new Pacman(board, 50, 50);

        board.setLayout(new BorderLayout());
        setLayout(new BorderLayout());


        board.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        board.setBackground(Color.YELLOW);
        //------
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setBackground(Color.black);

        board.add(pacman);



        add(board, BorderLayout.CENTER);
        add(livesPanel, BorderLayout.PAGE_END);

        setFocusable(true);
        addKeyListener(new MoveContol(pacman));


    }

    private void makeLives(){
        lives = 3;
        livesPanel = new JPanel();
        for(int i = 0; i < lives; i++){
            JLabel live = new JLabel( new ImageIcon("img/heart.png") );
            livesPanel.add(live);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(pacman.getCharacter(), pacman.getPosition().x, pacman.getPosition().y, null);
        for(int i = 0; i < lives; i++){
            g.drawImage(new ImageIcon("img/heart.png").getImage(), i + 50, 10, null);
        }
    }

    public JPanel getPanel(){
        return this;
    }

}
