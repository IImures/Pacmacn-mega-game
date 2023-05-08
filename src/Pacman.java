
import java.awt.*;

public class Pacman extends Entity{

//    public Pacman(JPanel window, int x, int y ){
//        super(window,x,y);
//
//    }
    public Pacman(String path, int y, int x){
        super(path,y ,x);
    }
    public Pacman(Image img, int y, int x){
        super(img,y ,x);
    }
}

