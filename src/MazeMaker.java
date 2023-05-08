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

//        Point current = new Point(1, 1);
//        while (visited.size() != 1000){
//            Point next = checkNext(current);
//            if (next != null) {
//                int x = (current.x + next.x) / 2;
//                int y = (current.y + next.y) / 2;
//                maze[x][y] = 1;
//
//                visited.add(current);
//                current = next;
//                maze[current.x][current.y] = 1;
//
//            } else if (!visited.isEmpty()) {
//                current = visited.get(visited.size() - 1);
//            }
//
//
//        }
        //show maze
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
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

        final int n = 4; // number of neighbors

        // the options of cells
        Point[] options = { new Point(current.x, current.y + 1), new Point(current.x, current.y - 1),
                new Point(current.x + 1, current.y), new Point(current.x - 1, current.y) };

        boolean[] goodIndices = new boolean[n]; // the options
        int nGood = 0; // number of good

        for (int i = 0; i < n; i++) {
            Point c = options[i];

            boolean good = c.x >= 0 && c.x < height && c.y >= 0 && c.y < width && maze[c.x][c.y] == 0;
            goodIndices[i] = good;

            if (good)
                nGood++;
        }

        if (nGood == 0)
            return null; // if there are no neighbors

        int rand = (int) (Math.random() * n);
        while (!goodIndices[rand]) {
            rand = (int) (Math.random() * n);
        }

        return options[rand]; // return the random neighbor

    }

    private boolean hasNext(Point current){
        final int n = 4; // number of neighbors

        // the options of cells
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


//    private boolean r_findRoute(Station station, Station destiny, ArrayList<Station> history){
//        for(Map.Entry<Station, Double> entry : station.getConnections().entrySet()){
//            if(entry.getKey().getConnections().containsKey(destiny) || entry.getKey().equals(destiny)){
//                Route.add(entry.getKey());
//                return true;
//            }
//            if(!history.contains(entry.getKey())){
//                history.add(entry.getKey());
//                if(r_findRoute(entry.getKey(), destiny, history)){
//                    Route.add(entry.getKey());
//                    return true;
//                }
//            }
//        }
//        return false;
//    }



}
