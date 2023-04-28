import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreMenu extends JPanel {

    private JPanel scoreMenu;
    private JFrame window;

    public ScoreMenu(JFrame window){
        this.window = window;

        scoreMenu = new JPanel(new GridBagLayout());

        JLabel label = new JLabel("mrmfergmerpg");
        JButton exit = new JButton("Return");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.remove(scoreMenu);
                window.repaint();
                window.add(new MenuWindow(window).getMainPanel());
                window.setSize(680, 480);

            }
        });
        scoreMenu.add(exit);
        scoreMenu.add(label);
    }

    public JPanel getScoreMenu(){
        return scoreMenu;
    }

}
