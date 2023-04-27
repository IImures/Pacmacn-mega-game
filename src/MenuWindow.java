import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {
    public JLabel mainPanel;

    public MenuWindow(){

        //Image mainPanel = Toolkit.getDefaultToolkit().getImage("img/MainMenuBG.jpg");

        //JPanel mainPanel = new JPanel();

        ImageIcon img = new ImageIcon("img/MainMenuBG.jpg");
        mainPanel = new JLabel(img){
            public void paintComponent(Graphics g) {
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), null);// draw the image
            }
        };
        //mainPanel.setBounds(0,0,500,500);

        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        JLabel title = new JLabel("PACMAN MEGAGAME");
        title.setFont(new Font("Chalkboard", Font.BOLD,27));
        title.setForeground(Color.magenta);
        title.setPreferredSize(new Dimension(275,50));

        mainPanel.add(title, gbc);


        JPanel buttons = new JPanel(new GridBagLayout());

        JButton startGame = new JButton("Start Game");
        startGame.setPreferredSize(new Dimension(150, 50));
        startGame.setOpaque(false);
        startGame.setContentAreaFilled(false);
        startGame.setBorderPainted(false);

        JButton score = new JButton("Score");
        score.setPreferredSize(new Dimension(100, 50));
        score.setOpaque(false);
        score.setContentAreaFilled(false);
        score.setBorderPainted(false);

        JButton exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(100, 50));
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);

        buttons.add(startGame);
        buttons.add(score);
        buttons.add(exit);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        mainPanel.add(startGame, gbc);
        mainPanel.add(score, gbc);
        mainPanel.add(exit, gbc);
        mainPanel.add(buttons, gbc);
        mainPanel.setPreferredSize(new Dimension(640,480));

    }
}
