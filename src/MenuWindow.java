import javax.swing.*;
import java.awt.*;


public class MenuWindow extends JPanel {
    private JLabel mainPanel;
    private JFrame window;

    public MenuWindow(JFrame window){
        this.window = window;
        ImageIcon img = new ImageIcon("img/MainBG.png");
        mainPanel = new JLabel(img){
            public void paintComponent(Graphics g) {
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), null);// draw the image
            }
        };

        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        new MainMenuButtons(mainPanel, window);

        mainPanel.setPreferredSize(new Dimension(640,480));
    }

    public JLabel getMainPanel(){
        return mainPanel;
    }
}
