import javax.swing.*;
import javax.swing.event.ListSelectionListener;
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

        exit = new JButton("Return");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                window.setVisible(true);
                window.setSize(680, 480);

            }
        });

        listPanel = new JPanel();
        listPanel.setBackground(Color.black);
        list = new JList<>(scoreList);
        list.setCellRenderer(new MyListCellRenderer());
        list.setOpaque(false);
        //list.setForeground(Color.MAGENTA);
        //list.setFont(list.getFont().deriveFont(16f));
        scrollPane = new JScrollPane(list);
        scrollPane.setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(320, getHeight() - (exit.getHeight()*2 + 5)));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(exit, BorderLayout.PAGE_END);
        listPanel.add(scrollPane);
        listPanel.setOpaque(false);
        add(listPanel,BorderLayout.CENTER);
        setVisible(true);
        repaint();
        setSize(getWidth(), getHeight()+1);
    }

    private void readScore(){
        try {
            Scanner scanner = new Scanner(new File("Score.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                scoreList.add(line);
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
        scrollPane.setPreferredSize(new Dimension(320, getHeight() - (exit.getHeight()*2 + 5) ));
    }
}

class MyListCellRenderer extends JLabel implements ListCellRenderer{

    MyListCellRenderer(){

    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        setText((String) value);
        setOpaque(true);
        setFont(getFont().deriveFont(16f));
        setBackground(Color.black);
        setForeground(Color.MAGENTA);
        return this;
    }
}
