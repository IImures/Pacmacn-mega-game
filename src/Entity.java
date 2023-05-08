import javax.swing.*;
import java.awt.*;


//enum ENTITY{
//    PacMan
//
//}

public abstract class Entity extends JLabel{

    private Image character;
    private boolean isSolid = false;

    private int xVelocity = 0;
    private int yVelocity = 0;

    private Point position;


    public Entity(String path, int y, int x) {
        super(new ImageIcon(path));
        setOpaque(true);
        position = new Point(x, y);
        character = new ImageIcon(path).getImage().getScaledInstance(40, 40,Image.SCALE_FAST);
        //setBounds(0,0, 50,50);
        setAlignmentX(CENTER_ALIGNMENT);
    }
    public Entity(Image img, int y, int x) {
        super(new ImageIcon(img));
        setOpaque(true);
        position = new Point(x, y);
        character = img;
        //setBounds(0,0, 50,50);
        setAlignmentX(CENTER_ALIGNMENT);
    }
    public Entity(String path, int y, int x, boolean isSolid) {
        super(new ImageIcon(path));
        setOpaque(true);
        position = new Point(x, y);
        character = new ImageIcon(path).getImage().getScaledInstance(40, 40,Image.SCALE_SMOOTH);
        this.isSolid = isSolid;
        //setBounds(0,0, 50,50);
        setAlignmentX(CENTER_ALIGNMENT);
    }



//    public Entity(JPanel window){
//        this.window = window;
//        character = new ImageIcon("img/pacman.png").getImage();
//        position = new Point(100,10);
//        //sssetPreferredSize(new Dimension(50,50));
//        move();
//    }
//    public Entity(JTable window, int x, int y){
//        //super(new ImageIcon("img/pacman.png"));
//        this.window = window;
//        character = new ImageIcon("img/MADGhost1.PNG").getImage();
//        position = new Point(x, y);
//        setPreferredSize(new Dimension(50,50));
//        setOpaque(false);
//        move();
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        Graphics2D g2d = (Graphics2D) g;
//
//        g2d.drawImage(character, position.x, position.y, null);
//
//    }
//
//    private void move(){
//        Thread thread = new Thread(()->{
//            while (true) {
//                try {
//                    //System.out.println(name + position);
//                    int x_axi = window.getWidth() - character.getWidth(null);
//                    int y_axi = window.getHeight() - character.getHeight(null);
//                    if(position.x > x_axi) {
//                        xVelocity *= 0;
//                        position.x = x_axi-1;
//                    }
//                    if(position.x < 1){
//                        xVelocity *= 0;
//                        position.x = 1;
//                    }
//                    if(position.y > y_axi){
//                        yVelocity *= 0;
//                        position.y = y_axi;
//                    }
//                    if(position.y < 1){
//                        yVelocity *= 0;
//                        position.y = 1;
//                    }
//
//                    position.x = position.x + xVelocity;
//                    position.y = position.y + yVelocity;
//                    repaint();
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        thread.start();
//    }
//
    public Image getCharacter() {
        return character;
    }

    public void setCharacter(Image character) {
        this.character = character;
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
    public boolean getIsSolid(){
        return isSolid;
    }
}

