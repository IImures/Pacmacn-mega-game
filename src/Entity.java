import javax.swing.*;
import java.awt.*;


//enum ENTITY{
//    PacMan
//
//}

public class Entity extends JLabel{

    private Image character;
    private JPanel window;

    private int xVelocity = 0;
    private int yVelocity = 0;
    String name;

    private Point position;

    public Entity(JPanel window){
        this.window = window;
        character = new ImageIcon("img/pacman.png").getImage();
        position = new Point(100,10);
        setPreferredSize(new Dimension(50,50));
        move();
    }
    public Entity(JPanel window, int x, int y, String name){
        this.name = name;
        this.window = window;
        character = new ImageIcon("img/pacman.png").getImage();
        position = new Point(x, y);
        setPreferredSize(new Dimension(50,50));
        setOpaque(false);
        move();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d =(Graphics2D) g;

        g2d.drawImage(character, position.x, position.y, null);

    }

    private void move(){
        Thread thread = new Thread(()->{
            while (true) {
                try {
                    System.out.println(name + position);
                    int x_axi = window.getWidth() - character.getWidth(null);
                    int y_axi = window.getHeight() - character.getHeight(null);
                    if(position.x > x_axi) {
                        xVelocity *= 0;
                        position.x = x_axi;
                    }
                    if(position.x < 1){
                        xVelocity *= 0;
                        position.x = 1;
                    }
                    if(position.y > y_axi){
                        yVelocity *= 0;
                        position.y = y_axi;
                    }
                    if(position.y < 1){
                        yVelocity *= 0;
                        position.y = 1;
                    }

                    position.x = position.x + xVelocity;
                    position.y = position.y + yVelocity;
                    repaint();
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public Image getCharacter() {
        return character;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
