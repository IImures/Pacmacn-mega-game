import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;


public class GameWindow extends JFrame {
    private JFrame window;
    private Level level;

    private String name;
    private String score;
    private int lives;


    public GameWindow(JFrame window){
        this.window = window;
        window.setVisible(false);

        setBackground(Color.black);
        setLayout(new BorderLayout());
        level = new Level(this);

        add(level, BorderLayout.CENTER);

        pack();
        //setSize(500, 500);
        //setMinimumSize(new Dimension(100,100));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void askForName(){

        level = null;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;



        JTextField nameSpace = new JTextField();
        nameSpace.setPreferredSize(new Dimension(150, 25));
        nameSpace.setOpaque(false);

        nameSpace.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_ENTER) {
                    if (nameSpace.getText().equals(""))
                        name = "Player Unknown";
                    else
                        name = nameSpace.getText();
                    try {
                        FileWriter fileWriter = new FileWriter("Score.txt", true);
                        fileWriter.write("Player: " + name + ", score: " + score + " lives left: " + lives + "\n");
                        fileWriter.close();
                    } catch (IOException ex) {
                        System.out.println("Error in filewriter");
                    }
                    setVisible(false);
                    window.setVisible(true);
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JLabel lable = new JLabel("You WIN!\nWrite your name and press enter");
        lable.setForeground(Color.BLACK);
        lable.setPreferredSize(new Dimension(275, 25));
        lable.setOpaque(false);
        lable.setBackground(Color.black);


        add(lable ,gbc);
        add(nameSpace, gbc);
        pack();

    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
