import javax.swing.*;

public class Window extends JFrame {

    private JPanel scoreMenu;
    public Window(){


        add(new MenuWindow(this).getMainPanel());

        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
