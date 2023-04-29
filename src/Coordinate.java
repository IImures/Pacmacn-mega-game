public class Coordinate {
    private int PosX;
    private int PosY;

    public Coordinate(int x, int y){
        this.PosX = x;
        this.PosY = y;
    }

    public int getPosX() {
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosX(int posX) {
        PosX = posX;
    }

    public void setPosY(int posY) {
        PosY = posY;
    }
}
