import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Level extends JPanel {
    private Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
    private int wight = 16;
    private int height = 16;
    private int BLOCK_SIZE = 32;

    private Pacman pacman;
    private Ghost[] ghosts = new Ghost[4];
    private int lives;
    private JLabel score;
    private JLabel time;
    private JPanel livesPanel;
    private JPanel boardPanel;

    private AbstractTableModel abstractTableModel;
    private Tile[] tiles = new Tile[3];
    private Thread gameThread;
    private Thread[] ghostThreads = new Thread[4];
    private volatile boolean isAlive = true;
    private JTable boardData;
    private int[][] maze;
    private KeyListener moveControl;

    private GameWindow window;

    private String[] ghostsImg = new String[]{
            "img/MAD.png",
            "img/PJC.png",
            "img/PJC.png",
            "img/MAD.png"
    };


    public Level(GameWindow window){
        this.window = window;
        setLayout(new BorderLayout());
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resize();
                repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        //addKeyListener(new QuitMenu(this));
        maze = new MazeMaker(height, wight).makeMaze();
        loadTiles();
        lives = 3;
        makeLives();


        makeBoard();
//        boardData.setValueAt(ghosts[0], 6,6);
//        boardData.setValueAt(ghosts[1], 7,7);
        placePacman();
        moveControl = new MoveContol(pacman);
        addKeyListener(moveControl);
        placeGhosts();


        add(livesPanel,BorderLayout.PAGE_END);

        //setFocusable(true);
        setFocusable(true);

        startGame();
        //test();

    }

    private void makeBoard(){
        boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);

        boardData = new JTable();
        boardData.setDefaultRenderer(Object.class, new ImageRenderer());
        abstractTableModel = new Board(new Object[height][wight], null);
        boardData.setModel(abstractTableModel);
        boardData.setRowSelectionAllowed(false);
        boardData.setIntercellSpacing(new Dimension(0, 0));
        boardData.setShowGrid(false);


        //board.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        boardData.setRowHeight(BLOCK_SIZE);
        //boardData.setMinimumSize(new Dimension(10,10));

        TableColumnModel columnModel = boardData.getColumnModel();
        for(int i = 0; i < boardData.getColumnCount(); i++){
            TableColumn column = columnModel.getColumn(i);
            column.setMinWidth(1);
            column.setCellRenderer(new ImageRenderer());
            column.setPreferredWidth(BLOCK_SIZE);
        }

        //boardData.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        boardData.setBackground(Color.black);
        translateMaze();
        //boardPanel.add(pacman);
        boardPanel.add(boardData);
        boardPanel.setFocusable(false);
        boardData.setFocusable(false);

        add(boardPanel);

    }

    private void translateMaze(){
        for(int y = 0; y < boardData.getColumnCount(); y++)
            for(int x = 0; x < boardData.getRowCount(); x++){
                if(maze[y][x] == 2 || maze[y][x] == 0 )
                    boardData.setValueAt(tiles[0], x, y);// Walld
                    //boardData.setValueAt(new Tile("img/Tile.png", y, x , true, false), x, y);
                else
                    boardData.setValueAt(tiles[1], x, y); //White dot
                //boardData.setValueAt(new Tile("img/WhiteDot.png", y,x, false, true), x, y);
            }
    }
    private void loadTiles(){
        tiles[0] = new Tile("img/Tile.png", true, false);
        tiles[1] = new Tile("img/WhiteDot.png", false, true);
        tiles[2] = new Tile("img/Black.png", false, false);
    }

    private void makeLives(){
        livesPanel = new JPanel();
        livesPanel.addKeyListener(new QuitMenu(this));
        livesPanel.setFocusable(false);
        Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH);
        ImageIcon heart = new ImageIcon(icon);
        livesPanel = new JPanel(new FlowLayout());
        livesPanel.add(new JLabel("Your lives: "));
        livesPanel.setSize(100, 50);
        for(int i = 0; i < lives; i++){
            JLabel live = new JLabel(heart);
            livesPanel.add(live);
        }
        score = new JLabel("0");
        livesPanel.add(new JLabel("Your score: "));
        livesPanel.add(score);
        livesPanel.add(new JLabel("Time: "));
        time = new JLabel("0");
        livesPanel.add(time);
        livesPanel.repaint();

    }

    public void resize() {
        //try {
            int availableWidth = boardData.getParent().getWidth();
            int availableHeight = boardData.getParent().getHeight();
            int columnCount = boardData.getColumnCount();
            int columnSize = availableWidth / columnCount;
            int rowCount = boardData.getRowCount();
            int rowSize = availableHeight / rowCount;
            BLOCK_SIZE = Math.min(rowSize, columnSize);
            if(BLOCK_SIZE < 1)
                BLOCK_SIZE = 1;

            boardData.setRowHeight(BLOCK_SIZE);

            TableColumnModel columnModel = boardData.getColumnModel();
            for (int i = 0; i < columnCount; i++) {
                TableColumn column = columnModel.getColumn(i);
                column.setCellRenderer(new ImageRenderer());
                column.setPreferredWidth(BLOCK_SIZE);
            }
            pacman.reScale(BLOCK_SIZE);
        for (Ghost ghost : ghosts) ghost.reScale(BLOCK_SIZE);
        for (Tile tile : tiles) tile.reScale(BLOCK_SIZE);
    }

    private void placePacman(){
        for(int x = 0; x < wight; x++){
            for(int y = 0; y < height; y++){
                int rand = (int)(Math.random() * wight * height + 1);
                Object ent = boardData.getValueAt(y, x);
                if(rand == wight * height && ent instanceof Tile && ((Tile)ent).getIsEatable()){
                    pacman = new Pacman("img/pacman.png", y, x);
                }
            }
        }
        if(pacman == null) placePacman();
    }

    private void placeGhosts(){

        int count = 0;


        for(int i = 0; i < ghosts.length; i++){
            while (ghosts[i] == null){
                placeGhost(i, count);
            }
            count++;
        }

        for(Ghost gh : ghosts){
            System.out.println(gh);
        }


    }

    private void placeGhost(int index, int count){
        for(int x = 0; x < wight; x++){
            for(int y = 0; y < height; y++){
                int rand = (int)(Math.random() * wight * height + 1);
                Object ent = boardData.getValueAt(y, x);
                if(rand == wight * height && ent instanceof Tile && ((Tile)ent).getIsEatable()){
                    ghosts[index] = new Ghost(ghostsImg[count], y, x);
                }
            }
        }
    }


    public void startGame() {
        isAlive = true;
        //moveGhosts();
        moveGhosts();
        Thread timer = new Thread(()->{
           while (isAlive){
               time.setText(String.valueOf((Integer.parseInt(time.getText()) + 1)));
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });
        gameThread = new Thread(()->{
            try {
                while (isAlive) {

                    checkForWin();

                    movePacman();

                    livesPanel.repaint();
                    boardData.repaint();
                    boardPanel.repaint();
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("Stop Game");
            }catch (NullPointerException ex){
                isAlive = false;
                System.out.println("Hujnia");
            }
        });
        for(Thread th : ghostThreads) th.start();
        timer.start();
        gameThread.start();

    }
    private void checkForWin(){
        for(int i = 0; i < wight; i++){
            for(int j = 0; j < height; j++){
                Object ent = boardData.getValueAt(j, i);
                if(ent instanceof Tile && ((Tile)ent).getIsEatable())
                    return;
            }
        }
        win();
    }
//    private void moveGhosts1(){
//        for(int i = 0; i < ghosts.length; i++){
//            Ghost ghost = ghosts[i];
//            System.out.println(i);
//            int tmp_X = ghost.getxVelocity() + ghost.getPosition().x;
//            int tmp_Y = ghost.getyVelocity() + ghost.getPosition().y;
//            Object ent = boardData.getValueAt(tmp_Y, tmp_X);
//            if (ent instanceof Pacman) {
//                //died();
//                isAlive=false;
//            } else if (ent instanceof Tile && ((Tile) ent).getIsSolid() || ent instanceof Ghost) {
//                int rand = (int) (Math.random() * 4) + 1;
//                switch (rand) {
//                    case 1 -> {
//                        ghost.setxVelocity(0);
//                        ghost.setyVelocity(-1);
//                        System.out.println("1 " + rand);
//                    }
//                    case 2 -> {
//                        ghost.setxVelocity(0);
//                        ghost.setyVelocity(1);
//                        System.out.println("2 " + rand);
//                    }
//                    case 3 -> {
//                        ghost.setxVelocity(-1);
//                        ghost.setyVelocity(0);
//                        System.out.println("3 " + rand);
//                    }
//                    case 4 -> {
//                        ghost.setxVelocity(1);
//                        ghost.setyVelocity(0);
//                        System.out.println("4 " + rand);
//                    }
//                }
//            } else {
//                boardData.setValueAt(ent, ghost.getPosition().y, ghost.getPosition().x);
//                ghost.getPosition().x = ghost.getxVelocity() + ghost.getPosition().x;
//                ghost.getPosition().y = ghost.getyVelocity() + ghost.getPosition().y;
//                boardData.setValueAt(ghost, ghost.getPosition().y, ghost.getPosition().x);
//            }
//        }
//    }
    private void moveGhosts(){
        for(int i = 0; i < ghosts.length; i++){
            Ghost ghost = ghosts[i];
            System.out.println(i);
            int timeToSleep = 400 - (i * 100);
            ghost.setyVelocity(0);
            ghost.setxVelocity(1);
            ghostThreads[i] = new Thread(()->{
                while (isAlive) {
                    int tmp_X = ghost.getxVelocity() + ghost.getPosition().x;
                    int tmp_Y = ghost.getyVelocity() + ghost.getPosition().y;
                    Object ent = boardData.getValueAt(tmp_Y, tmp_X);
                    if (ent instanceof Pacman) {
                        //died();
                        //isAlive = false;
                        died();
                    } else if (ent instanceof Tile && ((Tile) ent).getIsSolid() || ent instanceof Ghost) {
                        int rand = (int) (Math.random() * 4) + 1;
                        switch (rand) {
                            case 1 -> {
                                ghost.setxVelocity(0);
                                ghost.setyVelocity(-1);
                            }
                            case 2 -> {
                                ghost.setxVelocity(0);
                                ghost.setyVelocity(1);
                            }
                            case 3 -> {
                                ghost.setxVelocity(-1);
                                ghost.setyVelocity(0);
                            }
                            case 4 -> {
                                ghost.setxVelocity(1);
                                ghost.setyVelocity(0);
                            }
                        }
                        continue;
                    } else {
                        boardData.setValueAt(ent, ghost.getPosition().y, ghost.getPosition().x);
                        ghost.getPosition().x = ghost.getxVelocity() + ghost.getPosition().x;
                        ghost.getPosition().y = ghost.getyVelocity() + ghost.getPosition().y;
                        boardData.setValueAt(ghost, ghost.getPosition().y, ghost.getPosition().x);
                    }
                    livesPanel.repaint();
                    boardData.repaint();
                    boardPanel.repaint();
                    try {
                        Thread.sleep(timeToSleep);
                    } catch (InterruptedException e) {
                        ghost.setxVelocity(0);
                        ghost.setyVelocity(0);
                    }
                }
            });
        }
    }

    private void movePacman(){
        int tmp_X = pacman.getxVelocity() + pacman.getPosition().x;
        int tmp_Y = pacman.getyVelocity() + pacman.getPosition().y;
        Object ent = boardData.getValueAt(tmp_Y, tmp_X);
        if(ent instanceof Ghost){
            //died();
            //isAlive = false;
            died();
        } else if(ent instanceof Tile && ((Tile)ent).getIsSolid()){
            pacman.setyVelocity(0);
            pacman.setxVelocity(0);
        } else if(ent instanceof Tile && ((Tile)ent).getIsEatable()){
            boardData.setValueAt(tiles[2], pacman.getPosition().y, pacman.getPosition().x);
            score.setText(String.valueOf((Integer.parseInt(score.getText()) + 10)));
            pacman.getPosition().x = pacman.getxVelocity() + pacman.getPosition().x;
            pacman.getPosition().y = pacman.getyVelocity() + pacman.getPosition().y;
            boardData.setValueAt(pacman, pacman.getPosition().y, pacman.getPosition().x);
        } else {
            boardData.setValueAt(ent, pacman.getPosition().y, pacman.getPosition().x);
            pacman.getPosition().x = pacman.getxVelocity() + pacman.getPosition().x;
            pacman.getPosition().y = pacman.getyVelocity() + pacman.getPosition().y;
            boardData.setValueAt(pacman, pacman.getPosition().y, pacman.getPosition().x);
        }

//            if( pacman.getPosition().x >= x_axi){
//                pacman.setxVelocity(0);
//            }
//            if(pacman.getPosition().x <= 0){
//                pacman.setxVelocity(0);
//            }
//            if(pacman.getPosition().y >= y_axi){
//                pacman.setyVelocity(0);
//            }
//            if(pacman.getPosition().y <= 0){
//                pacman.setyVelocity(0);
//            }

    }

    private void win(){
        removeAll();

        window.setScore(score.getText());
        window.setLives(lives);

        window.askForName();
        gameThread.interrupt();
//        removeAll();
//        setBackground(Color.black);
//
//        setLayout(new GridBagLayout());
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.anchor = GridBagConstraints.NORTH;
//
//        JTextField nameSpace = new JTextField();
//
//        JLabel lable = new JLabel("Write your name and press enter");
//        lable.setForeground(Color.WHITE);
//
//        add(lable);
//        add(nameSpace);
//        repaint();

    }

    private synchronized void stopAll(){
        try {
            gameThread.wait();
            System.out.println(3232323);
            for (Thread ghostThread : ghostThreads) ghostThread.wait();
            System.out.println(345345);
        } catch (InterruptedException ex) {
            ex.getStackTrace();
        }
    }

    private void replacePacman(boolean pacmanPlaced){
        for(int x = 0; x < wight; x++){
            for(int y = 0; y < height; y++){
                int rand = (int)(Math.random() * 100 + 1);
                Object ent = boardData.getValueAt(y, x);
                if(rand == 100 && ent instanceof Tile && ((Tile)ent).getIsEatable()){
                    pacman.getPosition().x = x;
                    pacman.getPosition().y = y;
                    pacmanPlaced = true;
                }
            }
        }
        if(!pacmanPlaced) replacePacman(false);
    }

    private void replaceGhosts(){
        for(int i = 0; i < ghosts.length; i++){
            replaceGhost(i, false);
        }
    }

    private void replaceGhost(int index, boolean isPlaced){
        for(int x = 0; x < wight; x++){
            for(int y = 0; y < height; y++){
                int rand = (int)(Math.random() * wight * height + 1);
                Object ent = boardData.getValueAt(y, x);
                if(rand == wight * height && ent instanceof Tile && ((Tile)ent).getIsEatable()){
                    ghosts[index].getPosition().x = x;
                    ghosts[index].getPosition().y = y;
                    isPlaced = true;
                }
            }
        }
        if(!isPlaced) replaceGhost(index, false);
    }

    private void died() {
        isAlive = false;

//        for(int i = 0; i < wight; i++){
//            for(int j = 0; j < height; j++){
//                Object ent = boardData.getValueAt(j, i);
//                if(ent instanceof Ghost)
//                    boardData.setValueAt(tiles[1], j, i);
//            }
//        }

        //stopAll();

        removeAll();

        lives--;
        makeLives();

        makeBoard();
        //placePacman();
        replacePacman(false);
        replaceGhosts();
        //placeGhosts();

        boardData.setValueAt(pacman, pacman.getPosition().y, pacman.getPosition().x);
        score.setText(String.valueOf((Integer.parseInt(score.getText()) + 10)));
        for(Ghost gh : ghosts)
            boardData.setValueAt(gh, gh.getPosition().y, gh.getPosition().x);
        add(livesPanel, BorderLayout.PAGE_END);

        repaint();
        setSize(getWidth(), getHeight()+1);

        Thread timer = new Thread(()->{
            int count = -3;
            time.setText(count + "");
            while (count != 0){
                try {
                    Thread.sleep(1000);
                    time.setText(count + 1 + "");
                    count++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            isAlive = true;
        });
        timer.start();
        try {
            timer.join();
        }catch (InterruptedException ex){
            System.out.println("Timer problem");
        }
        //for(Thread th : ghostThreads) th.start();
    }



}
