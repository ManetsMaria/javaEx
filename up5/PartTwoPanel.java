import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class PartTwoPanel extends JPanel {
        JTextArea ta;
        JList l;
        DateValidator dv;

        public PartTwoPanel(String str){
            ta=new JTextArea(str);
            ta.setEditable(false);
            ta.setSize(400,300);
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);
            dv=new DateValidator();
            ArrayList <String> d = new ArrayList<String>();
            String [] text=str.split(" ");
            for (int i=0; i<text.length; i++){
                if (dv.validate(text[i])){
                    d.add(text[i]);
                }
            }
            text = new String [d.size()];
            d.toArray(text);
            l=new JList(text);
            this.add(new JScrollPane(ta));
            this.add(new JScrollPane(l));
        }
}