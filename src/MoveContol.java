import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MoveContol extends KeyAdapter {

    private GameWindow window;
    private Entity pacman;

    public MoveContol(Entity ent, GameWindow window){
        this.window = window;
        pacman = ent;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_A){
            pacman.setxVelocity(-1);
            pacman.setyVelocity(0);
        }
        if(key == KeyEvent.VK_D){
            pacman.setxVelocity(1);
            pacman.setyVelocity(0);
        }
        if(key == KeyEvent.VK_W){
            pacman.setxVelocity(0);
            pacman.setyVelocity(-1);
        }
        if(key == KeyEvent.VK_S){
            pacman.setxVelocity(0);
            pacman.setyVelocity(1);
        }
        if (e.isControlDown() && e.isShiftDown() && key == KeyEvent.VK_Q) {
            window.endGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
