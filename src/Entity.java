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

    private Point position;

    public Entity(JPanel window){
        this.window = window;
        character = new ImageIcon("img/pacman.png").getImage();
        position = new Point(100,10);
        setPreferredSize(new Dimension(50,50));
        move();
    }
    public Entity(JPanel window, int x, int y){
        this.window = window;
        character = new ImageIcon("img/pacman.png").getImage();
        position = new Point(x, y);
        setPreferredSize(new Dimension(50,50));
        setOpaque(false);//////////////
        move();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        //System.out.println(position);
        g2D.drawImage(character, position.x, position.y, null);
    }

    private void move(){
        Thread thread = new Thread(()->{
            while (true) {
                try {
                    //System.out.println("Move " + position);
                    int x_axi = window.getWidth() - character.getWidth(null);
                    if(position.x >= x_axi) {
                        xVelocity *= -1;
                        System.out.println(111111);
                        position.x = x_axi;
                    }
                    if(position.x <0){
                        xVelocity *= -1;
                    }
                    if(position.y >= window.getHeight() - character.getHeight(null)){
                        yVelocity *= -1;
                        //position.y = window.getHeight() - character.getHeight(null);
                    }
                    if(position.y < 0){
                        yVelocity *= -1;
                    }

                    position.x = position.x + xVelocity;
                    position.y = position.y + yVelocity;
                    repaint();
                    window.repaint();
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
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
