import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuButtons extends JPanel {

    public MainMenuButtons(JLabel mainPanel, JFrame window){

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        JButton startGame = new JButton("Start Game");
        startGame.setPreferredSize(new Dimension(150, 50));
        startGame.setOpaque(false);
        startGame.setContentAreaFilled(false);
        startGame.setBorderPainted(false);

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(()-> new GameWindow(window));
                window.setVisible(false);

            }
        });

        JButton score = new JButton("Score");
        score.setPreferredSize(new Dimension(100, 50));
        score.setOpaque(false);
        score.setContentAreaFilled(false);
        score.setBorderPainted(false);

        score.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                SwingUtilities.invokeLater(()->new ScoreMenu(window));
            }
        });

        JButton exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(100, 50));
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);


        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        mainPanel.add(startGame, gbc);
        mainPanel.add(score, gbc);
        mainPanel.add(exit, gbc);
    }

}
