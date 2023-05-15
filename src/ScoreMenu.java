import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class ScoreMenu extends JFrame {

    private JPanel scoreMenu;
    private JFrame window;
    private Vector<String> scoreList = new Vector<>();
    private JList list;
    private JPanel listPanel;
    private JScrollPane scrollPane;
    private JButton exit;

    public ScoreMenu(JFrame window){
        this.window = window;
        setLocationRelativeTo(null);
        pack();
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.BLACK);

        setLayout(new BorderLayout());
        readScore();
        listPanel = new JPanel();
        listPanel.setBackground(Color.black);
        list = new JList<>(scoreList);
        scrollPane = new JScrollPane(list);
        listPanel.setPreferredSize(new Dimension(320, getHeight()));
        exit = new JButton("Return");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                window.setVisible(true);
                window.setSize(680, 480);

            }
        });

        add(exit, BorderLayout.PAGE_END);
        listPanel.add(scrollPane);
        add(listPanel,BorderLayout.CENTER);
        paint(getGraphics());
        setVisible(true);
    }

    private void readScore(){
        try {
            Scanner scanner = new Scanner(new File("Score.txt"));
            while (scanner.hasNextLine()) {
                String tmp = scanner.nextLine();
                System.out.println(tmp);
                scoreList.add(tmp);
            }
        }catch (IOException ex){
            System.out.println("Scanner error");
        }
    }

    public JPanel getScoreMenu(){
        return scoreMenu;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,getWidth(),getHeight());
        scrollPane.setPreferredSize(new Dimension(320, getHeight() - (exit.getHeight()*2 + 5) ));
    }
}
