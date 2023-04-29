
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MoveContol extends KeyAdapter {

    private Entity pacman;

    public MoveContol(Entity ent){
        System.out.println("KeyPressed");
        pacman = ent;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("KeyPressed");
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
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
