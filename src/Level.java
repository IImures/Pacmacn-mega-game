import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


public class Level extends JPanel {
    private Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
    private int wight = 25;
    private int height = 25;
    private int BLOCK_SIZE = 32;

    private Pacman pacman;
    private Ghost[] ghosts = new Ghost[4];
    private int lives;
    private JLabel score;
    private JPanel livesPanel;
    private JPanel boardPanel;

    private AbstractTableModel abstractTableModel;
    private Tile[] tiles = new Tile[3];
    private Thread gameThread;
    private Thread[] ghostThreads = new Thread[4];
    private boolean isAlive = true;
    private JTable boardData;
    private int[][] maze;


    public Level(){
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
        pacman = new Pacman("img/pacman.png", 5, 5);
        ghosts[0] = new Ghost("img/MAD.png", 6,6);
        ghosts[1] = new Ghost("img/PJC.png", 7,7);
        lives = 3;
        addKeyListener(new MoveContol(pacman));
        makeLives();


        makeBoard();
        boardData.setValueAt(ghosts[0], 6,6);
        boardData.setValueAt(ghosts[1], 7,7);



        add(livesPanel,BorderLayout.PAGE_END);

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
        loadTiles();
        maze = new MazeMaker(boardData.getRowCount(), boardData.getColumnCount()).makeMaze();
        translateMaze();
        boardData.setValueAt(pacman, 5,5);
        //boardPanel.add(pacman);

        boardData.setVisible(true);
        boardData.setFocusable(false);

        boardPanel.add(boardData);

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
        tiles[2] = new Tile("img/Black.png", false, true);
    }

    private void makeLives(){
        livesPanel = new JPanel();
        livesPanel.setFocusable(true);
        Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH);
        ImageIcon heart = new ImageIcon(icon);
        livesPanel = new JPanel(new FlowLayout()){
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                for(int i = 0; i < lives; i++)
//                    g.drawImage(icon,  this.getWidth() + i * 28,this.getHeight(),null);
//            }
        };
        livesPanel.add(new JLabel("Your lives: "));
        livesPanel.setSize(100, 50);
        for(int i = 0; i < lives; i++){
            JLabel live = new JLabel(heart);
            livesPanel.add(live);
        }
        score = new JLabel("0");
        livesPanel.add(new JLabel("Your score: "));
        livesPanel.add(score);
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
            //BLOCK_SIZE = Math.min(rowSize, columnSize);
            //pacman.setCharacter(pacman.getCharacter().getScaledInstance(BLOCK_SIZE, BLOCK_SIZE, Image.SCALE_SMOOTH));
            //pacman.setBounds(0,0, BLOCK_SIZE,BLOCK_SIZE);
            //pacman = new Pacman(pacman.getCharacter().getScaledInstance(BLOCK_SIZE, BLOCK_SIZE, Image.SCALE_SMOOTH), pacman.getPosition().y, pacman.getPosition().x);
            //pacman = pacman;
            //addKeyListener(new MoveContol(pacman));

            boardData.setRowHeight(BLOCK_SIZE);

            TableColumnModel columnModel = boardData.getColumnModel();
            for (int i = 0; i < columnCount; i++) {
                TableColumn column = columnModel.getColumn(i);
                column.setCellRenderer(new ImageRenderer());
                column.setPreferredWidth(BLOCK_SIZE);
            }
            pacman.reScale(BLOCK_SIZE);
            for(int i = 0; i < ghosts.length - 2; i++)
                ghosts[i].reScale(BLOCK_SIZE);
            for(int i = 0; i < tiles.length; i++)
                tiles[i].reScale(BLOCK_SIZE);
//            for(int y = 0; y < boardData.getColumnCount(); y++)
//                for(int x = 0; x < boardData.getRowCount(); x++){
//                    Entity ent = (Entity) boardData.getValueAt(x,y);
//                    ent.reScale(BLOCK_SIZE + 2);
//                    //ent.setCharacter(ent.getCharacter().getScaledInstance(BLOCK_SIZE + 1, BLOCK_SIZE + 1, Image.SCALE_REPLICATE));
//                }
       // }catch (IllegalArgumentException ex) {
          //  System.out.println(ex);
        //}
    }


//
//
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
//        ImageIcon heart = new ImageIcon("img/heart.png");
//        for(int i = 0; i < lives; i++)
//            livesPanel.add(new JLabel(heart));
//            //g.drawImage(icon, livesPanel.getWidth() +i * 30, livesPanel.getHeight(), null);
//    }

    private void test(){
        Thread theead = new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lives--;
            System.out.println(lives);
        });
        theead.start();
    }


    public void startGame() {
        moveGhosts();

        gameThread = new Thread(()->{
            try {
                moveGhosts();
                while (isAlive) {

                    movePacman();


                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("Stop Game");
            }
        });
        for(int i = 0 ; i < 2; i++){
            ghostThreads[i].start();
        }

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
        died();
    }
    private void moveGhosts(){
        for(int i = 0; i < ghosts.length - 2; i++){
            Ghost ghost = ghosts[i];
            int timeToSleep = 400 - (i * 100);
            ghost.setyVelocity(0);
            ghost.setxVelocity(1);
            ghostThreads[i] = new Thread(()->{
                while (isAlive) {
                    int tmp_X = ghost.getxVelocity() + ghost.getPosition().x;
                    int tmp_Y = ghost.getyVelocity() + ghost.getPosition().y;
                    Object ent = boardData.getValueAt(tmp_Y, tmp_X);
                    if (ent instanceof Pacman) {
                        died();
                        System.out.println("Died Pacman");
                    } else if (ent instanceof Tile && ((Tile) ent).getIsSolid() || ent instanceof Ghost) {
                        int rand = (int) (Math.random() * 4) + 1;
                        switch (rand) {
                            case 1 -> {
                                ghost.setxVelocity(0);
                                ghost.setyVelocity(-1);
                                System.out.println("1 a" + rand);
                            }
                            case 2 -> {
                                ghost.setxVelocity(0);
                                ghost.setyVelocity(1);
                                System.out.println("2 " + rand);
                            }
                            case 3 -> {
                                ghost.setxVelocity(-1);
                                ghost.setyVelocity(0);
                                System.out.println("3 " + rand);
                            }
                            case 4 -> {
                                ghost.setxVelocity(1);
                                ghost.setyVelocity(0);
                                System.out.println("4 " + rand);
                            }
                        }
                        continue;
                    } else {
                        boardData.setValueAt(ent, ghost.getPosition().y, ghost.getPosition().x);
                        ghost.getPosition().x = ghost.getxVelocity() + ghost.getPosition().x;
                        ghost.getPosition().y = ghost.getyVelocity() + ghost.getPosition().y;
                        boardData.setValueAt(ghost, ghost.getPosition().y, ghost.getPosition().x);
                    }
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
        int x_axi = boardData.getColumnCount() - 1;
        int y_axi = boardData.getRowCount() - 1;

        int tmp_X = pacman.getxVelocity() + pacman.getPosition().x;
        int tmp_Y = pacman.getyVelocity() + pacman.getPosition().y;
        Object ent = boardData.getValueAt(tmp_Y, tmp_X);
        if(ent instanceof Ghost){
            died();
            System.out.println("DIES!!!!");
            pacman.setyVelocity(0);
            pacman.setxVelocity(0);
        }
        if(ent instanceof Tile && ((Tile)ent).getIsSolid()){
            pacman.setyVelocity(0);
            pacman.setxVelocity(0);
        } else if(ent instanceof Tile && ((Tile)ent).getIsEatable()){
                boardData.setValueAt(tiles[2], pacman.getPosition().y, pacman.getPosition().x);
                score.setText(String.valueOf((Integer.parseInt(score.getText()) + 10)));
            }else {
                boardData.setValueAt(ent, pacman.getPosition().y, pacman.getPosition().x);
            }
            pacman.getPosition().x = pacman.getxVelocity() + pacman.getPosition().x;
            pacman.getPosition().y = pacman.getyVelocity() + pacman.getPosition().y;
            boardData.setValueAt(pacman, pacman.getPosition().y, pacman.getPosition().x);
            if( pacman.getPosition().x >= x_axi){
                pacman.setxVelocity(0);
            }
            if(pacman.getPosition().x <= 0){
                pacman.setxVelocity(0);
            }
            if(pacman.getPosition().y >= y_axi){
                pacman.setyVelocity(0);
            }
            if(pacman.getPosition().y <= 0){
                pacman.setyVelocity(0);
            }

//        livesPanel.repaint();
//        boardData.repaint();
//        boardPanel.repaint();
    }

    private void died() {
        isAlive = false;

        removeAll();

        translateMaze();
        add(boardPanel);

        lives -= 1;
        makeLives();
        add(livesPanel, BorderLayout.PAGE_END);

        setSize(getWidth(),getHeight() + 1); //kostyl


        repaint();

    }


}
