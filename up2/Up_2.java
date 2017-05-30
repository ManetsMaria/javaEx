import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Tur {
 
        private ImageIcon flag;
        private String name;
        private int price;
 
        public Tur(ImageIcon flag, String name, int price) {
            this.flag=flag;
            this.name=name;
            this.price=price;
        }
        public Tur() {
        }
        public void setFlag (ImageIcon f){
            flag=f;
        }
        public void setName (String n){
            name=n;
        }
        public void setPrice (int p){
            price=p;
        }
        public ImageIcon getFlag() {
            return flag;
        }
 
        public String getName() {
            return name;
        }
 
        public int getPrice() {
            return price;
        }
    }
public class Up_2 {

    private static final CheckModel model = new CheckModel();
    private static JMenuItem add = new JMenuItem("Add/Change");
    private static Add dialog = null;
    private static final JTable table = new JTable(model) {

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return new Dimension(350, 300);
        }
    };
    private class DisplayPanel extends JPanel {
        private DefaultListModel dlm = new DefaultListModel();
        private JList list = new JList(dlm);
        public DisplayPanel(final CheckModel model) {
            super(new GridLayout());
            this.setBorder(BorderFactory.createTitledBorder("Checked"));
            this.add(new JScrollPane(list));

            model.addTableModelListener(new TableModelListener() {

                @Override
                public void tableChanged(TableModelEvent e) {
                    dlm.removeAllElements();
                    int summa=0;
                    for (Integer integer : model.checked) {
                        dlm.addElement(model.getValueAt(integer, 2));
                        summa+=(int) model.getValueAt(integer, 2);
                    }
                    dlm.addElement("Summa: "+ summa);
                }

            });
        }
    }
    private class Add extends javax.swing.JDialog{
        private Tur tur=new Tur();
        private boolean result=false;
        private JTextField country = new JTextField("Country");
        private JButton im = new JButton("Choose Image");
        private JButton change = new JButton("Change/Add");
        private JTextField price = new JTextField ("Price");
        public Add(java.awt.Frame parent){
            super(parent, true);
            JPanel p = new JPanel();
            this.add(p);
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS)); 
            p.add(im);
            p.add(country);
            p.add(price);
            p.add(change);
        
            im.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        JFileChooser fileopen = new JFileChooser();
                        int ret = fileopen.showDialog(null, "Open file");
                        if (ret == JFileChooser.APPROVE_OPTION) {
                            File file= fileopen.getSelectedFile();
                            tur.setFlag(new ImageIcon(ImageIO.read(file)));
                            im.setText("Ok");
                        }
                    }
                    catch(IOException e)
                    {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    catch(NullPointerException w)
                    {
                        JOptionPane.showMessageDialog(null, w);
                    }
                }
            });
            change.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent e) {
                    tur.setName(country.getText());
                    try{
                        tur.setPrice(Integer.parseInt(price.getText()));
                        result=true;
                    }
                    catch (NumberFormatException a) {
                        result=false;
                    }
                    finally{ // пользователь нажал ОК
                        if (im.getText().compareTo("Ok")!=0)
                            result=false;
                         dispose();
                    }
                }
            });
        }
        public Tur getParametr(){
            return tur;
        }
         
        private void jButtonCancelMouseClicked(java.awt.event.MouseEvent evt) {
            this.result = false; // пользователь нажал Отмена
            this.dispose(); // уничтожить окно
        }
        public boolean execute() { // метод для "общения" с родительским окном
            this.setVisible(true); // прорисовать дочернее окно; в данном месте выполнение программы приостановится, ожидая окончания работы пользователя (в нашем случае - функции dispose())
            return this.result; // вернуть в качестве результата условное значение нажатой кнопки
        }
    }
    private class CheckModel extends AbstractTableModel {

        private int rows;
        private List<Boolean> rowList;
        private Set<Integer> checked = new TreeSet<Integer>();
        private ArrayList <Tur> turs=new ArrayList <Tur>();
        final Random random = new Random();

        public CheckModel() {
            turs=new ArrayList<Tur>();
            try{
               Scanner in = new Scanner(new File("country and capital.txt"));
               String nextCountry;
               String nextCapital;
               while (in.hasNext()){
                    nextCountry=in.next();
                    nextCapital=in.next();
                    turs.add(new Tur (new ImageIcon (nextCountry + ".png"),nextCountry, Math.abs(random.nextInt(1000))));
                }
            }
            catch(FileNotFoundException e){
                JOptionPane.showMessageDialog(null, "no file");
            }
            this.rows = turs.size();
            rowList = new ArrayList<Boolean>(rows);
            for (int i = 0; i < rows; i++) {
                rowList.add(Boolean.FALSE);
            }
        }

        public void addNewRow(Tur tur)
        {
            boolean change=false;
            int cur=0;
            Iterator<Tur> itr = turs.iterator();
            while (itr.hasNext()) {
              Tur t=itr.next();
              if (t.getName().compareTo(tur.getName())==0)
              {
                change=true;
                t.setPrice(tur.getPrice());
                t.setFlag(tur.getFlag());
                //rowList.set(cur,false);
              }
              cur++;
            }
            if (!change){
                turs.add(tur);
                rowList.add(Boolean.FALSE);
                rows++;
            }
            fireTableDataChanged();
        }
        @Override
        public int getRowCount() {
            return turs.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
            case 0:
                return "Image";
            case 1:
                return "Country";
            case 2:
                return "Price";
            case 3:
                return "Choise tur";
            }
            return "";
        }

        @Override
        public Object getValueAt(int row, int col) {
            Tur tur = turs.get(row);
 
            switch (col) {
                case 0:
                    return tur.getFlag();
                case 1:
                    return tur.getName();
                case 2:
                    return tur.getPrice();
                case 3:
                    return rowList.get(row);
            }
         
            return "";
        }

        @Override
        public void setValueAt(Object aValue, int row, int col) {
            boolean b = (Boolean) aValue;
            rowList.set(row, b);
            if (b) {
                checked.add(row);
            } else {
                checked.remove(row);
            }
            fireTableRowsUpdated(row, row);

        }

        @Override
        public Class<?> getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return true;
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("CheckTable"); 
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setLayout(new GridLayout(1, 0));
                JPanel p=new JPanel();
                p.setLayout(new BorderLayout());
                f.add(p);
                p.add(new JScrollPane(table));
                f.add(new DisplayPanel(model));
                JMenuBar menuBar = new JMenuBar();
                menuBar.setBackground(Color.GRAY);
                JMenu fileMenu = new JMenu("Menu");
                menuBar.add(fileMenu);
                fileMenu.add(add);
                p.add(menuBar, BorderLayout.NORTH);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    dialog = new Add(f);
                    dialog.setPreferredSize(new Dimension(200, 200));
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    if (dialog.execute()) {
                        Tur tur=dialog.getParametr();
                        model.addNewRow(tur);

                    }
                    else {       // действия при нажатии на клавишу отмены   
                    }
                    }
                });
            }
        });
    }
}