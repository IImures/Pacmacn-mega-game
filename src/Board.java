import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Board extends AbstractTableModel {

    Object[][] board;
    String[] uselessShit;

    public Board(Object[][] things, String[] uselessShit){
        this.board = things;
        this.uselessShit = uselessShit;

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //super.setValueAt(aValue, rowIndex, columnIndex);
        board[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return board.length;
    }

    @Override
    public int getColumnCount() {
        return board[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return board[rowIndex][columnIndex];
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

}

class ImageRenderer extends DefaultTableCellRenderer {

    public ImageRenderer(){
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        // Call the parent method to set up the default cell rendering

        Component c= super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
//        setHorizontalAlignment(JLabel.CENTER);
//        setVerticalAlignment(JLabel.CENTER);
        Entity rendererComponent = (Entity) value;
//        rendererComponent.setHorizontalAlignment(SwingConstants.CENTER);
//        rendererComponent.setVerticalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(1,16,1,-1));
        setIcon(((Entity) value).getIcon());

//        // If the value is an ImageIcon, set it as the icon for the label
//        if (value instanceof Entity) {
//            setIcon(((Entity) value).getIcon());
//            setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
//        }else {
//            // Clear the icon from the label
//            setIcon(null);
//        }

        return this;
    }
}



