import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class QuitMenu extends KeyAdapter {
    private JPanel panel;

    public QuitMenu(JPanel panel){
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_P) {

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
