import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameWindow extends JFrame {
    private JFrame window;
    private Level level;

    private int timeTable[] = new int[3];
    private int scoreTable[] = new int[3];
    private String message;
    private String name;

    public GameWindow(JFrame window){
        this.window = window;
        window.setVisible(false);

        setBackground(Color.black);

        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        JLabel label = new JLabel("Input (width)x(height) to create maze, and press enter");
        label.setFont(new Font("Arcade Classic Regular", Font.PLAIN, 15));
        JTextField tField = new JTextField();
        tField.setPreferredSize(new Dimension(200,25));

        panel.add(label, gbc);
        panel.add(tField, gbc);
        panel.setPreferredSize(new Dimension(400,200));
        add(panel);

        tField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_ENTER) {
                    String input = tField.getText();

                    if(input.equals(""))
                        return;
                    Pattern regexPattern = Pattern.compile("(\\d+)x(\\d+)");

                    Matcher matcher = regexPattern.matcher(input);

                    if (matcher.find()) {
                        int wight = Integer.parseInt(matcher.group(1));
                        int height = Integer.parseInt(matcher.group(2));
                        System.out.println(wight);
                        System.out.println(height);
                            if(wight < 10 || height < 10 || wight > 100 || height > 100) {
                                label.setText("Wrong format! Input (width)x(height)");
                                tField.setText("");
                            }else{
                                startGame(wight, height);
                                remove(panel);
                                remove(label);
                                remove(tField);
                            }
                    } else {
                        System.out.println("Pattern not found in input string.");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

//        setLayout(new BorderLayout());
//        level = new Level(this);
//
//        add(level, BorderLayout.CENTER);

        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame(int wight, int height){
        setLayout(new BorderLayout());
        setSize(640, 480);
        level = new Level(this, wight, height );

        add(level, BorderLayout.CENTER);
    }

    public void endGame(){
        level.interruptAll();
        level = null;
        dispose();
        setVisible(false);
        window.setVisible(true);
    }
    public void exit(){
        dispose();
        setVisible(false);
        window.setVisible(true);
    }

    public void askForName(){
        level.interruptAll();
        level = null;
        setLayout(new GridBagLayout());
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (e.isControlDown() && e.isShiftDown() && key == KeyEvent.VK_Q) {
                    exit();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;



        JTextField nameSpace = new JTextField();
        nameSpace.setPreferredSize(new Dimension(200, 25));
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
                if (e.isControlDown() && e.isShiftDown() && key == KeyEvent.VK_Q) {
                    exit();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JLabel lable = new JLabel(message);
        lable.setForeground(Color.BLACK);
        lable.setPreferredSize(new Dimension(350, 25));
        lable.setOpaque(false);
        lable.setBackground(Color.black);


        add(lable ,gbc);
        add(nameSpace, gbc);
        setResizable(false);
        pack();

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
