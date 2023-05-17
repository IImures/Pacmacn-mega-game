import javax.swing.*;
import java.awt.*;

public abstract class Entity extends JLabel{

    private Image character;
    private String path;
    private boolean isSolid = false;
    private boolean isEatable = false;

    private int xVelocity = 0;
    private int yVelocity = 0;

    private Point position;


    public Entity(String path, int y, int x) {
        super(new ImageIcon(path));
        setOpaque(true);
        position = new Point(x, y);
        character = new ImageIcon(path).getImage();
        this.path = path;
    }

    public Entity(String path, boolean isSolid, boolean isEatable) {
        super(new ImageIcon(path));
        setOpaque(true);
        character = new ImageIcon(path).getImage();
        this.isSolid = isSolid;
        this.isEatable = isEatable;
        this.path = path;
    }

    public void reScale(int BLOCK_SIZE){
        Image character = new ImageIcon(path).getImage().getScaledInstance(BLOCK_SIZE, BLOCK_SIZE, Image.SCALE_FAST);
        setBounds(0,0,BLOCK_SIZE,BLOCK_SIZE);
        setCharacter(character);
    }

    public void setReScaled(int BLOCK_SIZE, String path){
        Image character = new ImageIcon(path).getImage().getScaledInstance(BLOCK_SIZE, BLOCK_SIZE, Image.SCALE_FAST);
        setBounds(0,0,BLOCK_SIZE,BLOCK_SIZE);
        setCharacter(character);
    }

    public void setCharacter(Image character) {
        this.character = character;
        ImageIcon newIcon = new ImageIcon(character);
        setIcon(newIcon);
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

    public boolean getIsSolid(){
        return isSolid;
    }
    public boolean getIsEatable(){
        return isEatable;
    }
}

