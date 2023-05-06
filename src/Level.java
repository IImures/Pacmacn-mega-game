import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class Level extends JPanel {
    private Image icon = new ImageIcon("img/heart.png").getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
    private int wight = 11;
    private int height = 20;
    private int BLOCK_SIZE = 40;

    private Pacman pacman;
    private Ghost[] ghosts;
    private int lives;
    private JPanel livesPanel;

    private JTable board;
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
        makeLives();

        //board = new JPanel();
        //board.setSize(new Dimension(wight * BLOCK_SIZE, height * BLOCK_SIZE));
        makeBoard();

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
        pacman = new Pacman("img/pacman.png", 5, 5);
        ghosts[0] = new Ghost("img/MADGhost1.png", 5,10);
        addKeyListener(new MoveContol(pacman));

        board.setValueAt(pacman, 5 ,5);
        board.setValueAt(ghosts[0], 5 ,10);
//        board.setValueAt(new Pacman("img/pacman.png",3, 3), 3 ,3);
        startGame();
        test();

    }

    private void makeBoard(){
        JPanel panel = new JPanel();
        board = new JTable();
        board.setModel(new Board(new Object[height][wight], null));

        //board.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        board.setRowHeight(BLOCK_SIZE);
        TableColumnModel columnModel = board.getColumnModel();

        for(int i = 0; i < board.getColumnCount(); i++){
            TableColumn imageColumn = columnModel.getColumn(i);
            imageColumn.setCellRenderer(new ImageRenderer());
            columnModel.getColumn(i).setPreferredWidth(BLOCK_SIZE);
        }

        //JScrollPane scrollPane = new JScrollPane(board);
        //add(scrollPane, BorderLayout.CENTER);

        board.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        board.setBackground(Color.black);

        panel.add(board);
        board.setFocusable(true);
        panel.setFocusable(true);
        add(panel);

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

//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        //g.drawImage(pacman.getCharacter(), pacman.getPosition().x, pacman.getPosition().y, null);
//        for(int i = 0; i < lives; i++){
//            g.drawImage(icon, i + 50, livesPanel.getY(), null);
//        }
//    }
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
        int x_axi = board.getColumnCount() - 1;
        int y_axi = board.getRowCount() - 1;

        int tmp_X = pacman.getxVelocity() + pacman.getPosition().x;
        int tmp_Y = pacman.getyVelocity() + pacman.getPosition().y;

        if(board.getValueAt(tmp_Y, tmp_X) instanceof Ghost){
            System.out.println("KNNNNKNKN");
            pacman.setyVelocity(0);
            pacman.setxVelocity(0);
        }else {
            board.setValueAt(null, pacman.getPosition().y, pacman.getPosition().x);
            pacman.getPosition().x = pacman.getxVelocity() + pacman.getPosition().x;
            pacman.getPosition().y = pacman.getyVelocity() + pacman.getPosition().y;
            board.setValueAt(pacman, pacman.getPosition().y, pacman.getPosition().x);
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
        board.repaint();
    }

    public JPanel getPanel(){
        return this;
    }

}
