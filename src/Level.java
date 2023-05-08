import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;


public class Level extends JPanel {
    private Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
    private int wight = 34;
    private int height = 25;
    private int BLOCK_SIZE = 40;

    private Pacman pacman;
    private Ghost[] ghosts;
    private int lives;
    private JPanel livesPanel;
    private JPanel boardPanel;

    private JTable boardData;
    private JTable level;
//    private JFrame window;
//
//    public Level(JFrame window){
//        this.window = window;
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setSize(new Dimension(wight * BLOCK_SIZE, height * BLOCK_SIZE));
//        panel.setBorder(BorderFactory.createLineBorder(Color.RED));
//        panel.setBackground(Color.black);
//        pacman1 = new Entity(panel, 50, 50);
//        pacman2 = new Entity(panel, 50, 50);
//        panel.add(pacman1);
//        panel.add(pacman2);
//        panel.setFocusable(true);
//        panel.addKeyListener(new MoveContol(pacman1));
//        setVisible(true);
//    }


    public Level(){
        setLayout(new BorderLayout());
        pacman = new Pacman("img/pacman.png", 5, 5);
        addKeyListener(new MoveContol(pacman));
        makeLives();

        //board = new JPanel();
        //board.setSize(new Dimension(wight * BLOCK_SIZE, height * BLOCK_SIZE));

        makeBoard();
        repaint();

        ghosts = new Ghost[4];
        //pacman = new Pacman(board, 50, 50);

//        board.setLayout(new BorderLayout());

        //------
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setBackground(Color.red);

//        board.add(pacman);


        add(livesPanel,BorderLayout.PAGE_END);

        setFocusable(true);
        //addKeyListener(new MoveContol(pacman));
        //add(new Pacman("img/pacman.png"));
//        pacman = new Pacman("img/pacman.png", 3, 3);
        ghosts[0] = new Ghost("img/MADGhost1.png", 5,10);
        //board.addKeyListener(new MoveContol(pacman));

        //boardData.setValueAt(pacman, 3 ,3);
        //boardData.setValueAt(ghosts[0], 5 ,10);
//        board.setValueAt(new Pacman("img/pacman.png",3, 3), 3 ,3);



        startGame();
        //test();

    }

    private void makeBoard(){
        boardData = new JTable();
        boardData.setModel(new Board(new Object[height][wight], null));
        boardData.setFocusable(false);
        boardData.setRowSelectionAllowed(false);


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

        boardData.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        boardData.setBackground(Color.black);
        int[][] maze = new MazeMaker(boardData.getRowCount(), boardData.getColumnCount()).makeMaze();
        for(int y = 0; y < boardData.getColumnCount(); y++)
            for(int x = 0; x < boardData.getRowCount(); x++){
                if(maze[y][x] == 2 || maze[y][x] == 0 )
                    boardData.setValueAt(new Tile("img/Tile.png", y, x , true), x, y);
                else
                    boardData.setValueAt(new Tile("img/Black.png", y,x, false), x, y);
            }
        boardData.setBackground(Color.black);
        boardData.setValueAt(pacman, 5,5);
        boardPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int xPos = 0;
                int yPos = 0;
                int col = 0;
                int row =0;
                while(col < boardData.getColumnCount() && row < boardData.getRowCount()){
                    Entity ent = (Entity) boardData.getValueAt(row, col);
                    g.drawImage(ent.getCharacter(), xPos, yPos, null);
                    col++;
                    xPos +=BLOCK_SIZE;
                    if(col == boardData.getColumnCount()){
                        col = 0;
                        xPos = 0;
                        row++;
                        yPos += BLOCK_SIZE;
                    }
                }
            }
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                int xPos = 0;
//                int yPos = 0;
//                for(int x = 0; x < boardData.getRowCount(); x++){
//                    for(int y = 0; y < boardData.getColumnCount(); y++){
//                        Entity ent = (Entity) boardData.getValueAt(x,y);
//                        g.drawImage(ent.getCharacter(), xPos,yPos, null);
//                        xPos += BLOCK_SIZE;
//                    }
//                    yPos +=BLOCK_SIZE;
//                }
//            }
        };

        //boardPanel.add(pacman);

        boardData.setVisible(false);

        boardPanel.add(boardData);

        boardPanel.setFocusable(false);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        add(boardPanel);
        //boardPanel.repaint();
    }

    private void makeLives(){
        lives = 3;
        livesPanel = new JPanel();
        livesPanel.setFocusable(true);
        Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        ImageIcon heart = new ImageIcon(icon);
//        livesPanel = new JPanel(new FlowLayout()){
//            private Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                for(int i = 0; i < lives; i++)
//                    g.drawImage(icon,  this.getWidth() + i * 28,this.getHeight(),null);
//            }
//        };
        livesPanel.add(new JLabel("Your lives: "));
        livesPanel.setSize(100, 50);
        for(int i = 0; i < lives; i++){
            JLabel live = new JLabel(heart);
            livesPanel.add(live);
        }
    }

    public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            int availableWidth = boardData.getParent().getWidth();
            int availableHeight = boardData.getParent().getHeight();
            int columnCount = boardData.getColumnCount();
            int columnSize = availableWidth / columnCount;
            int rowCount = boardData.getRowCount();
            int rowSize = availableHeight / rowCount;
            BLOCK_SIZE = Math.min(rowSize, columnSize);

            pacman.setCharacter(pacman.getCharacter().getScaledInstance(BLOCK_SIZE, BLOCK_SIZE, Image.SCALE_SMOOTH));
//            pacman = new Pacman(pacman.getCharacter().getScaledInstance(BLOCK_SIZE, BLOCK_SIZE, Image.SCALE_SMOOTH), pacman.getPosition().y, pacman.getPosition().x);
            boardData.setRowHeight(BLOCK_SIZE);


            TableColumnModel columnModel = boardData.getColumnModel();
            for (int i = 0; i < columnCount; i++) {
                TableColumn column = columnModel.getColumn(i);
                column.setCellRenderer(new ImageRenderer());
                column.setPreferredWidth(BLOCK_SIZE);
            }
        }catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }
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
        Thread thread = new Thread(()->{
            try {
                while (true) {

                    movePacman();

                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private void movePacman(){
        int x_axi = boardData.getColumnCount() - 1;
        int y_axi = boardData.getRowCount() - 1;

        int tmp_X = pacman.getxVelocity() + pacman.getPosition().x;
        int tmp_Y = pacman.getyVelocity() + pacman.getPosition().y;
//        try{
        System.out.println(tmp_X + "  " + tmp_Y);
        Entity ent = (Entity) boardData.getValueAt(tmp_Y, tmp_X);
        if(ent.getIsSolid()){
            System.out.println("KNNNNKNKN");
            pacman.setyVelocity(0);
            pacman.setxVelocity(0);
        }else {
            boardData.setValueAt(ent, pacman.getPosition().y, pacman.getPosition().x);
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
        }
        boardPanel.repaint();
//    }catch (ArrayIndexOutOfBoundsException ex){
//            System.out.println(ex);
//        }
    }

    public JPanel getPanel(){
        return this;
    }

}

//        boardData.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
//                int width = getSize().width;
//                int height = getSize().height;
//                if (width != height) {
//                    int size = Math.min(width, height);
//                    setSize(new Dimension(size, size));
//                    boardData.setRowHeight((panel.getHeight() - (getHeight() / width)) /  width);
//                    BLOCK_SIZE=size/100*95/width;
//                }
//            }
//        });

//JScrollPane scrollPane = new JScrollPane(board);
//add(scrollPane, BorderLayout.CENTER);


//        boardData.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
//                int width = panel.getSize().width;
//                int height = panel.getSize().height;
//                if (width != height) {
//                    int size = Math.min(width, height);
//                    setSize(new Dimension(size, size));
//                    boardData.setRowHeight((panel.getHeight() - (getHeight() / BLOCK_SIZE)) / BLOCK_SIZE);
//                }
//            }});