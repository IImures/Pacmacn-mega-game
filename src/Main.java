import javax.swing.*;

public class Main extends JFrame {

    private JPanel scoreMenu;
    public Main(){

        add(new MenuWindow(this).getMainPanel());

        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> new Main());
    }
}