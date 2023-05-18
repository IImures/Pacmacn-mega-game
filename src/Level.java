import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;


public class Level extends JPanel {
    private int wight;
    private int height;
    private int BLOCK_SIZE = 32;

    private Pacman pacman;
    private final Ghost[] ghosts = new Ghost[4];
    private int lives;
    private JLabel score;
    private JLabel time;
    private int timeTable[] = new int[3];
    private int scoreTable[] = new int[3];
    private JPanel livesPanel;
    private JPanel boardPanel;

    private AbstractTableModel abstractTableModel;
    private Tile[] tiles = new Tile[3];
    private PowerUp powerUpIcon;
    private Thread powerUp;
    private Thread timer;
    private Thread changingIcon;
    private Thread gameThread;
    private Thread[] ghostThreads = new Thread[4];
    private volatile boolean isAlive = true;
    private double speed = 1;
    private double ghostsSpeed = 1;
    private JTable boardData;
    private int[][] maze;
    private KeyListener moveControl;

    private GameWindow window;

    private String[] ghostsImg = new String[]{
            "img/PJC1.png",
            "img/MAD1.png",
            "img/PPJ1.png",
            "img/GUI1.png"
    };


    public Level(GameWindow window, int wight, int height){
        this.window = window;
        this.wight = wight;
        this.height = height;
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
        maze = new MazeMaker(height, wight).makeMaze();
        loadTiles();
        lives = 3;
        makeLives();


        makeBoard();

        placePacman();
        moveControl = new MoveContol(pacman, window);
        addKeyListener(moveControl);
        placeGhosts();


        add(livesPanel,BorderLayout.PAGE_END);

        setFocusable(true);

        startGame();

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


        boardData.setRowHeight(BLOCK_SIZE);

        TableColumnModel columnModel = boardData.getColumnModel();
        for(int i = 0; i < boardData.getColumnCount(); i++){
            TableColumn column = columnModel.getColumn(i);
            column.setMinWidth(1);
            column.setCellRenderer(new ImageRenderer());
            column.setPreferredWidth(BLOCK_SIZE);
        }

        boardData.setBackground(Color.black);
        translateMaze();

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
        powerUpIcon = new PowerUp("img/PowerUP.png", false, true);
    }

    private void makeLives(){
        livesPanel = new JPanel();
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
          powerUpIcon.reScale(BLOCK_SIZE);
    }

    private void placePacman(){
        for(int x = 0; x < wight; x++){
            for(int y = 0; y < height; y++){
                int rand = (int)(Math.random() * wight * height + 1);
                Object ent = boardData.getValueAt(y, x);
                if(rand == wight * height && ent instanceof Tile && ((Tile)ent).getIsEatable()){
                    pacman = new Pacman("img/STU1.png", y, x);
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
        moveGhosts();
        setPowerUp();
        timer = new Thread(()->{
           while (true){
               try {
                   if(!isAlive) {
                       Thread.sleep(10);
                       continue;
                   }
                   time.setText(String.valueOf((Integer.parseInt(time.getText()) + 1)));
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   System.out.println("Error in timer");
               }
           }
        });

        changingIcon = new Thread(()->{
            while (true) {
                try {
                    pacman.setReScaled(BLOCK_SIZE, "img/STU1.png");
                    ghosts[0].setReScaled(BLOCK_SIZE, "img/PJC1.png");
                    ghosts[1].setReScaled(BLOCK_SIZE, "img/MAD1.png");
                    ghosts[2].setReScaled(BLOCK_SIZE, "img/PPJ1.png");
                    ghosts[3].setReScaled(BLOCK_SIZE, "img/GUI1.png");
                    Thread.sleep(500);
                    pacman.setReScaled(BLOCK_SIZE, "img/STU2.png");
                    ghosts[0].setReScaled(BLOCK_SIZE, "img/PJC2.png");
                    ghosts[1].setReScaled(BLOCK_SIZE, "img/MAD2.png");
                    ghosts[2].setReScaled(BLOCK_SIZE, "img/PPJ2.png");
                    ghosts[3].setReScaled(BLOCK_SIZE, "img/GUI2.png");
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    System.out.println("Error while changing icons");
                }
            }
        });

        gameThread = new Thread(()->{
            try {
                while (true) {

                    if(!isAlive) {
                        try {
                            Thread.sleep(10);
                            continue;
                        } catch (InterruptedException e) {
                            System.out.println("Error in gameThread");
                        }
                    }

                    checkForWin();
                    movePacman();

                    livesPanel.repaint();
                    boardData.repaint();
                    boardPanel.repaint();
                    Thread.sleep((long) (500 * speed));
                }
            } catch (InterruptedException e) {
                System.out.println("Stop Game");
            }catch (NullPointerException ex){
                ex.getStackTrace();
            }
        });
        changingIcon.start();
        for(Thread th : ghostThreads) th.start();
        timer.start();
        gameThread.start();
        powerUp.start();
    }

    private void setPowerUp(){
        powerUp = new Thread(()->{
            try{
                while (true){
                    if(!isAlive) {
                        Thread.sleep(10);
                        continue;
                    }
                    Thread.sleep(5000);
                    System.out.println("PowerUP");
                    int rand = (int)(Math.random() * 4) + 1;
                    if(rand == 4)
                        makePowerUp();

                }
            }catch (InterruptedException ex){
                System.out.println("Error in main powerUP");
            }
        });
    }

    private void makePowerUp(){
        int rand = (int)(Math.random() * 4);
        int xVel = ghosts[rand].getxVelocity();
        int yVel = ghosts[rand].getyVelocity();
        Point p = ghosts[rand].getPosition();
        Object tmpO = boardData.getValueAt(p.y - yVel, p.x - xVel);
        if( tmpO instanceof Tile && !((Tile)tmpO).getIsSolid())
            boardData.setValueAt(powerUpIcon, p.y - yVel, p.x - xVel);
        repaint();
    }

    private void checkForWin(){
        for(int i = 0; i < wight; i++){
            for(int j = 0; j < height; j++){
                Object ent = boardData.getValueAt(j, i);
                if(ent instanceof Tile && ((Tile)ent).getIsEatable())
                    return;
            }
        }
        winOrLose(true);
    }
    private void moveGhosts(){
        for(int i = 0; i < ghosts.length; i++){
            Ghost ghost = ghosts[i];
            System.out.println(i);
            ghost.setyVelocity(0);
            ghost.setxVelocity(1);
            ghostThreads[i] = new Thread(()->{
                while (true) {
                    if(!isAlive) {
                        try {
                            Thread.sleep(10);
                            continue;
                        } catch (InterruptedException e) {
                            System.out.println("Error in moving ghosts");
                        }
                    }
                    int tmp_X = ghost.getxVelocity() + ghost.getPosition().x;
                    int tmp_Y = ghost.getyVelocity() + ghost.getPosition().y;
                    Object ent = boardData.getValueAt(tmp_Y, tmp_X);
                    if (ent instanceof Pacman) {
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
                        Thread.sleep((long) (400 * ghostsSpeed));
                    } catch (InterruptedException e) {
                        e.getStackTrace();
                        ghost.setxVelocity(0);
                        ghost.setyVelocity(0);
                    }
                }
            });
        }
    }

    private void activatePowerUp(){
        int rand = (int)(Math.random() * 5) + 1;
        switch (rand){
            case 1 -> score.setText(String.valueOf((Integer.parseInt(score.getText()) + 100)));
            case 2 ->{
                Thread speedP = new Thread(()->{
                    speed = 0.75;
                    try{
                        Thread.sleep(5000);
                        speed = 1;
                    }catch (InterruptedException ex){
                        System.out.println("Error in powerUP 2");
                    }
                });
                speedP.start();
            }
            case 3 ->{
                Thread speedP = new Thread(()->{
                    ghostsSpeed = 0.75;
                    try{
                        Thread.sleep(5000);
                        ghostsSpeed = 1;
                    }catch (InterruptedException ex){
                        System.out.println("Error in powerUP 3");
                    }
                });
                speedP.start();
            }
            case 4 ->{
                Thread speedP = new Thread(()->{
                    speed = 1.2;
                    try{
                        Thread.sleep(5000);
                        speed = 1;
                    }catch (InterruptedException ex){
                        System.out.println("Error in powerUP 4");
                    }
                });
                speedP.start();
            }
            case 5 ->{
                Thread speedP = new Thread(()->{
                    ghostsSpeed = 10;
                    try{
                        Thread.sleep(5000);
                        ghostsSpeed = 1;
                    }catch (InterruptedException ex){
                        System.out.println("Error in powerUP 5");
                    }
                });
                speedP.start();
            }
        }

    }

    private void movePacman(){
        int tmp_X = pacman.getxVelocity() + pacman.getPosition().x;
        int tmp_Y = pacman.getyVelocity() + pacman.getPosition().y;
        Object ent = boardData.getValueAt(tmp_Y, tmp_X);
        if(ent instanceof Ghost){
            died();
        }
        else if(ent instanceof PowerUp){
            activatePowerUp();
            boardData.setValueAt(tiles[2], pacman.getPosition().y, pacman.getPosition().x);
            pacman.getPosition().x = pacman.getxVelocity() + pacman.getPosition().x;
            pacman.getPosition().y = pacman.getyVelocity() + pacman.getPosition().y;
            boardData.setValueAt(pacman, pacman.getPosition().y, pacman.getPosition().x);
        }
        else if(ent instanceof Tile && ((Tile)ent).getIsSolid()){
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
    }

    private void winOrLose(boolean winOrLose){
        removeAll();

        if(winOrLose)
            window.setMessage("God Job! input your name and press Enter");
        else
            window.setMessage("ITN wasn't done! Input your name and press Enter");

        window.setScoreTable(scoreTable);
        window.setTimeTable(timeTable);

        window.askForName();
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

        removeAll();
        timeTable[3 - lives] = Integer.parseInt(time.getText());
        scoreTable[3 - lives] = Integer.parseInt(score.getText());
        lives--;
        if(lives == 0) {
            winOrLose(false);
            return;
        }

        makeLives();

        makeBoard();

        replacePacman(false);
        replaceGhosts();
        repaint();


        boardData.setValueAt(pacman, pacman.getPosition().y, pacman.getPosition().x);
        score.setText(String.valueOf((Integer.parseInt(score.getText()) + 10)));
        for(Ghost gh : ghosts)
            boardData.setValueAt(gh, gh.getPosition().y, gh.getPosition().x);
        add(livesPanel, BorderLayout.PAGE_END);

        repaint();

        setSize(getWidth(), getHeight()+1);

        Thread timer = new Thread(()->{
            int count = -2;
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
    }

    public void interruptAll(){
        removeKeyListener(moveControl);
        try {
            for (Thread th : ghostThreads) th.interrupt();
            gameThread.interrupt();
            timer.interrupt();
            changingIcon.interrupt();
        }catch (NullPointerException ex){
            System.out.println("Error in interruptAll");
        }
    }
}
