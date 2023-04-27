import javax.swing.*;
import java.awt.*;


public class MenuWindow extends JFrame {
    public JLabel mainPanel;

    public MenuWindow(){

        ImageIcon img = new ImageIcon("img/MainMenuBG.jpg");
        mainPanel = new JLabel(img){
            public void paintComponent(Graphics g) {
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), null);// draw the image
            }
        };

        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        JLabel title = new JLabel("PACMAN MEGAGAME");
        title.setFont(new Font("Chalkboard", Font.BOLD,27));
        title.setForeground(Color.magenta);
        title.setPreferredSize(new Dimension(275,50));

        mainPanel.add(title, gbc);
        new MainMenuButtons(mainPanel);

        mainPanel.setPreferredSize(new Dimension(640,480));

    }
}
