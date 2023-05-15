import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class GameWindow extends JFrame {
    private JFrame window;
    private Level level;

    private int timeTable[] = new int[3];
    private int scoreTable[] = new int[3];
    private String message;
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
                        fileWriter.write("\n---===Player: " + name + "===---\n");
                        fileWriter.write("Attempt 1: Score: " + scoreTable[0] + ", Time: " + timeTable[0] + "\n");
                        for(int i = 1; i < scoreTable.length; i++)
                            if(scoreTable[i] != 0) fileWriter.write("Attempt " + (i+1)  + ": Score: " + scoreTable[i] + ", Time: " + timeTable[i] + "\n");
                        fileWriter.write("---======---\n");
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

        JLabel lable = new JLabel(message);
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

    public void setMessage(String message){
        this.message = message;
    }

    public void setTimeTable(int[] timeTable) {
        this.timeTable = timeTable;
    }

    public void setScoreTable(int[] scoreTable) {
        this.scoreTable = scoreTable;
    }
}
