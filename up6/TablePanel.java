import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.DateFormat;
import java.util.Optional;


public class TablePanel extends JPanel {
    public TablePanel(TableModel model){
        setLayout(new BorderLayout());
        JTable mainTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(mainTable);
        JTable rowTable = new RowNumberTable(mainTable);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
        add(scrollPane);

    }
}
