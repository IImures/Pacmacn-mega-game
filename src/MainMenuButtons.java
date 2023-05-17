import javax.swing.*;
import java.awt.*;


public class MainMenuButtons extends JPanel {

    public MainMenuButtons(JLabel mainPanel, JFrame window){

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        JButton startGame = new JButton("Start Game");
        startGame.setPreferredSize(new Dimension(200, 50));
        startGame.setFont(new Font("Montserrat", Font.BOLD, 20));
        startGame.setForeground(Color.WHITE);
        startGame.setOpaque(false);
        startGame.setContentAreaFilled(false);
        startGame.setBorderPainted(false);

        startGame.addActionListener(e -> {
            SwingUtilities.invokeLater(()-> new GameWindow(window));
            window.setVisible(false);

        });

        JButton score = new JButton("Score");
        score.setFont(new Font("Montserrat", Font.BOLD, 20));
        score.setForeground(Color.WHITE);
        score.setPreferredSize(new Dimension(100, 50));
        score.setOpaque(false);
        score.setContentAreaFilled(false);
        score.setBorderPainted(false);

        score.addActionListener(e -> {
            window.setVisible(false);
            SwingUtilities.invokeLater(()->new ScoreMenu(window));
        });

        JButton exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(100, 50));
        exit.setFont(new Font("Montserrat", Font.BOLD, 20));
        exit.setForeground(Color.WHITE);
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);


        exit.addActionListener(e -> System.exit(0));

        mainPanel.add(startGame, gbc);
        mainPanel.add(score, gbc);
        mainPanel.add(exit, gbc);
    }

}
