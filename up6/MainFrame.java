import javafx.util.Pair;
import javax.swing.table.AbstractTableModel;
import java.util.regex.Matcher;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainFrame extends JFrame {
    private static JTable table;
    public MainFrame(int columns, int rows){

        MyTableModel model = new MyTableModel(columns, rows);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLocationByPlatform(true);
        setLayout(new BorderLayout());
        JPanel panel=new JPanel();
        add(panel);
        panel.setLayout(new BorderLayout());
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JTable rowTable = new RowNumberTable(table);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
        panel.add(scrollPane);
    }
    class MyTableModel extends AbstractTableModel {
        private String[] columnNames;
        private String[][] values;
        private String[][] strings;
        private int columns, rows;
        private Boolean round[][];
        private Pattern datePattern = Pattern.compile("(\\d+)[.](\\d+)[.](\\d+)");
        private Pattern longDatePattern = Pattern.compile("[=](\\d+)[.](\\d+)[.](\\d+)[+-](\\d+)");
        private Pattern letterDatePattern = Pattern.compile("^[=]([A-Z](\\d+))[+-](\\d+)$");
        private Pattern funcPattern = Pattern.compile("^[=]((MIN)|(MAX))[(](((\\d+)[.](\\d+)[.](\\d+)[,])|([A-Z](\\d+)[,]))*(((\\d+)[.](\\d+)[.](\\d+))|([A-Z](\\d+)))[)]");
        private Matcher m;

        MyTableModel(int columns, int rows) {
            this.columns=columns;
            this.rows=rows;
            columnNames = new String[columns];
            values = new String[rows][columns];
            strings = new String[rows][columns];
            round = new Boolean[rows][columns];
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < columns; ++j) {
                    round[i][j] = false;
                    strings[i][j] = "";
                    values[i][j] = "";
                }
            }
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return values.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return strings[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            boolean change = false;
            if (value != null) {
                values[row][col] = (String) value;
                String temp = (String) value;
                temp = temp.replaceAll(" ", "");
                String str = match(temp, row, col);
                if (!str.equals(strings[row][col])) {
                    strings[row][col] = match(temp, row, col);
                    change = true;
                }
            }

            if (change) {
                for (int i = 0; i < row; ++i) {
                    for (int j = 0; j < columns; ++j) {
                        setValueAt(values[i][j], i, j);
                    }
                }
            }
            fireTableCellUpdated(row, col);
        }

        private String match(String str, int row, int column) {
            if (round[row][column]) {
                return "Illegal link!";
            }
            Matcher dateMatcher = datePattern.matcher(str);
            Matcher longDateMatcher = longDatePattern.matcher(str);
            Matcher linkMatcher = letterDatePattern.matcher(str);
            Matcher funcMatcher = funcPattern.matcher(str);
            if (dateMatcher.matches()) {
                m = Pattern.compile("(\\d+)").matcher(str);
                m.find();
                int day = Integer.parseInt(m.group());
                m.find();
                int month = Integer.parseInt(m.group());
                month--;
                m.find();
                int year = Integer.parseInt(m.group());

                GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                return calendar.get(GregorianCalendar.DAY_OF_MONTH) + "." + (calendar.get(GregorianCalendar.MONTH) + 1) + "." + calendar.get(GregorianCalendar.YEAR);
            } else if (longDateMatcher.matches()) {
                m = datePattern.matcher(str);
                m.find();
                String dat = m.group();

                m = Pattern.compile("(\\d+)").matcher(dat);
                m.find();
                int day = Integer.parseInt(m.group());
                m.find();
                int month = Integer.parseInt(m.group());
                month--;
                m.find();
                int year = Integer.parseInt(m.group());

                m = Pattern.compile("[+-](\\d+)").matcher(str);
                m.find();
                int change = Integer.parseInt(m.group());

                GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                calendar.add(GregorianCalendar.DAY_OF_MONTH, change);
                return calendar.get(GregorianCalendar.DAY_OF_MONTH) + "." + (calendar.get(GregorianCalendar.MONTH) + 1) + "." + calendar.get(GregorianCalendar.YEAR);

            } else if (linkMatcher.matches()) {
                try {
                    int indexX = str.charAt(1) - 'A';
                    int indexY = str.charAt(2) - '0' - 1;
                    m = Pattern.compile("[+-](\\d+)").matcher(str);
                    m.find();
                    int change = Integer.parseInt(m.group());

                    char checker = new Character((char) ('A' + column));
                    round[row][column] = true;
                    table.setValueAt(values[indexY][indexX], indexY, indexX);
                    round[row][column] = false;
                    String temp1 = strings[indexY][indexX];
                    m = Pattern.compile("(\\d+)").matcher(temp1);
                    m.find();
                    int day = Integer.parseInt(m.group());
                    m.find();
                    int month = Integer.parseInt(m.group());
                    month--;
                    m.find();
                    int year = Integer.parseInt(m.group());

                    GregorianCalendar calendar = new GregorianCalendar(year, month, day);
                    calendar.add(GregorianCalendar.DAY_OF_MONTH, change);
                    return calendar.get(GregorianCalendar.DAY_OF_MONTH) + "." + (calendar.get(GregorianCalendar.MONTH) + 1) + "." + calendar.get(GregorianCalendar.YEAR);

                } catch (IllegalStateException | ArrayIndexOutOfBoundsException e) {
                    return "Illegal link!";
                } catch (NullPointerException e) {
                    return "No data in linked item!";
                }
            } else if (funcMatcher.matches()) {
                try {
                    SortedSet<GregorianCalendar> calendars = new TreeSet<>();

                    m = datePattern.matcher(str);
                    while (m.find()) {
                        String dat = m.group();
                        Matcher m1 = Pattern.compile("(\\d+)").matcher(dat);
                        m1.find();
                        int day = Integer.parseInt(m1.group());
                        m1.find();
                        int month = Integer.parseInt(m1.group());
                        month--;
                        m1.find();
                        int year = Integer.parseInt(m1.group());

                        GregorianCalendar tmpCalendar = new GregorianCalendar(year, month, day);
                        calendars.add(tmpCalendar);
                    }
                    m = Pattern.compile("[A-Z](\\d+)").matcher(str);
                    while (m.find()) {
                        String dat = m.group();
                        int indexX = dat.charAt(0) - 'A';
                        int indexY = dat.charAt(1) - '0' - 1;

                        round[row][column] = true;
                        table.getColumnModel().getColumn(indexX).getCellRenderer().getTableCellRendererComponent(table, values[indexY][indexX], false, false, indexY, indexX);
                        round[row][column] = false;
                        String temp1 = strings[indexY][indexX];
                        Matcher m1 = Pattern.compile("(\\d+)").matcher(temp1);
                        m1.find();
                        int day = Integer.parseInt(m1.group());
                        m1.find();
                        int month = Integer.parseInt(m1.group());
                        month--;
                        m1.find();
                        int year = Integer.parseInt(m1.group());

                        GregorianCalendar tmpCalendar = new GregorianCalendar(year, month, day);
                        calendars.add(tmpCalendar);
                    }
                    GregorianCalendar calendar;
                    if (str.contains("MAX"))
                        calendar = calendars.last();
                    else
                        calendar = calendars.first();

                    return calendar.get(GregorianCalendar.DAY_OF_MONTH) + "." + (calendar.get(GregorianCalendar.MONTH) + 1) + "." + calendar.get(GregorianCalendar.YEAR);

                } catch (NullPointerException e) {
                    return "No data in linked item!";
                } catch (IllegalStateException e) {
                    return "Incorrect data in linked item!";
                }
            } else {
                if (str.equals(""))
                    return "";
                else
                    return "Illegal data!";
            }
        }
    }
    public static void main (String args[]){
        MainFrame mainFrame = new MainFrame(10,5);
        mainFrame.setTitle("Excel");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}

