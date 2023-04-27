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
        mainPanel = new JLabel(img);
        //mainPanel.setBounds(0,0,500,500);

        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        mainPanel.add(new JLabel("<html><h1><strong><i>PACman MegaGame</i></strong></h1><hr></html>"), gbc);


        JPanel buttons = new JPanel(new GridBagLayout());

        JButton startGame = new JButton("Start Game");
        //startGame.setSize(100,100);
        JButton score = new JButton("Score");
        //score.setSize(100,100);
        JButton exit = new JButton("Exit");
        //exit.setSize(100,100);

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
        //mainPanel.setBounds(50,50,300,300);
        //mainPanel.add(mainPanel,gbc);

        //add(mainPanel);
        //add(mainPanel);


        //setSize(600,550);

    }
}
