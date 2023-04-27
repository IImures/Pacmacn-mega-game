import javax.swing.*;

public class Window extends JFrame {
    public Window(){

        add(new MenuWindow().mainPanel);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
