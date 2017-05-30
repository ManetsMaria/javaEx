import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;
import java.io.*;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
public class Up_1 extends JFrame {

     public Up_1() {
          super("Country and flags");
          createGUI();
     }

     public void createGUI() {
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          JPanel panel = new JPanel();
          panel.setLayout(new BorderLayout());
           ArrayList <String> d = new ArrayList<String>();
           Map <String, String> inf = new HashMap <String, String>();
           try{
	           Scanner in = new Scanner(new File("country and capital.txt"));
	           String nextCountry;
	           String nextCapital;
	           while (in.hasNext()){
		            nextCountry=in.next();
		            nextCapital=in.next();
		            d.add(nextCountry);
		            inf.put(nextCountry, nextCapital);
		        }
		    }
		    catch(FileNotFoundException e){
		    	JOptionPane.showMessageDialog(null, "no file");
		    }
	        String[] data = new String[d.size()];
			data =d.toArray(data);
            final JList list = new JList(data);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            list.setCellRenderer(new DefaultListCellRenderer() {
               public Component getListCellRendererComponent(JList list,
                         Object value, int index, boolean isSelected,
                         boolean cellHasFocus) {
                    Component a = super.getListCellRendererComponent(list,
                              value, index, isSelected, cellHasFocus);
                    ((JLabel)a).setIcon(new ImageIcon(value+".png"));
                    return a;
               }
            });

          panel.add(new JScrollPane(list), BorderLayout.CENTER);

          final JLabel label = new JLabel(" ");

          panel.add(label, BorderLayout.SOUTH);

          list.addListSelectionListener(
                    new ListSelectionListener() {
                         public void valueChanged(ListSelectionEvent e) {
                              Object element = list.getSelectedValue();
                              label.setIcon(new ImageIcon (element + ".png"));
                              label.setText(element.toString()+" "+inf.get(element));
                         }
                    });

          getContentPane().add(panel);

          setPreferredSize(new Dimension(250, 200));
     }

     public static void main(String[] args) {
           			Up_1 frame = new Up_1();
           			frame.setPreferredSize(new Dimension(500, 200));
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
     }
}