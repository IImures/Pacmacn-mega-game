import java.awt.*;
import java.util.ArrayList;

public class MazeMaker {

    private int width;
    private int height;

    private int[][] maze;
    private ArrayList<Point> visited;


    public MazeMaker(int width, int height){
        this.height = height;
        this.width = width;
        maze = new int[height][width];
        visited = new ArrayList<>();
    }

    public int[][] makeMaze(){

        makeFiledAndWalls();
        makePath();

        return maze;
    }

    private void makePath(){
        r_makePath(new Point(1, 1));
    }

    private boolean r_makePath(Point current){
        visited.add(current);
        //while (hasNext(current)){
        for(int i = 0; i < 2 && hasNext(current); i++){
            Point next = checkNext(current);
            if(next != null && isVisited(next)){
                maze[current.x][current.y] = 1;
                r_makePath(next);
            }else{
                return false;
            }
        }
        return true;
    }

    private boolean isVisited(Point target){
        for(Point point : visited){
            if(target.equals(point))
                return false;
        }
        return true;
    }

    private void makeFiledAndWalls(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                maze[i][j] = 0;
            }
        }
        for (int i = 0; i < height; i++) {
            maze[i][0] = 2;
            maze[i][width - 1] = 2;
        }
        for (int i = 0; i < width; i++) {
            maze[0][i] = 2;
            maze[height - 1][i] = 2;
        }

    }

    private Point checkNext(Point current) {

        final int n = 4;


        Point[] options = { new Point(current.x, current.y + 1), new Point(current.x, current.y - 1),
                new Point(current.x + 1, current.y), new Point(current.x - 1, current.y) };

        boolean[] goodIndices = new boolean[n];
        int nGood = 0;

        for (int i = 0; i < n; i++) {
            Point c = options[i];

            boolean good = c.x >= 0 && c.x < height && c.y >= 0 && c.y < width && maze[c.x][c.y] == 0;
            goodIndices[i] = good;

            if (good)
                nGood++;
        }

        if (nGood == 0)
            return null;

        int rand = (int) (Math.random() * n);
        while (!goodIndices[rand]) {
            rand = (int) (Math.random() * n);
        }

        return options[rand];

    }

    private boolean hasNext(Point current){
        final int n = 4;

        Point[] options = { new Point(current.x, current.y + 2), new Point(current.x, current.y - 2),
                new Point(current.x + 2, current.y), new Point(current.x - 2, current.y) };

        for (int i = 0; i < n; i++) {
            Point c = options[i];

            boolean good = c.x >= 0 && c.x < height && c.y >= 0 && c.y < width && (int)maze[c.x][c.y] == 0;
            if(good)
                return good;
        }
        return false;
    }
}
