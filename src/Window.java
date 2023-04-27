import javax.swing.*;

public class Window extends JFrame {
    public Window(){

        add(new MenuWindow().mainPanel);

        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
