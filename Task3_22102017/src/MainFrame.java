import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainFrame extends JFrame {

    private MenuPanel menuPanel;
    private DataAndCurrencyPanel dataAndCurrencyPanel;
    Locale current;

    public MainFrame() {
        super();
        initialize();
    }

    private void initialize() {
        initSystemLookAndFeel();
        this.setSize(500, 500);
        current = Locale.UK;
        menuPanel = new MenuPanel(this);
        dataAndCurrencyPanel = new DataAndCurrencyPanel(current);
        this.add(menuPanel, BorderLayout.NORTH);
        this.add(dataAndCurrencyPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        nameWindow();
    }

    private void initSystemLookAndFeel() {
        try {
            String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (UnsupportedLookAndFeelException e) {
            Logger.getLogger(MainFrame.class.getName()).log(new LogRecord(Level.WARNING, "Impossible use this theme"));
        } catch (Exception e) {
            Logger.getLogger(MainFrame.class.getName()).log(new LogRecord(Level.WARNING, "Unknow exception"));
         }
    }

    public Locale getCurrentLocale(){
        return current;
    }

    public void setCurrentLocale(Locale locale){
        current = locale;
        nameWindow();
        menuPanel.changeLocale(current);
        dataAndCurrencyPanel.change(current);
        UIManager.getDefaults().setDefaultLocale(locale);
        JComponent.setDefaultLocale(locale);
    }
    private void nameWindow(){
        try{
            ResourceBundle rb = ResourceBundle.getBundle("properties/words", current);
            String st = rb.getString("InterpreterWindow");
            this.setTitle(new String(st.getBytes("ISO-8859-1"), "UTF-8"));
        }
        catch (UnsupportedEncodingException e){
            Logger.getLogger(MainFrame.class.getName()).log(new LogRecord(Level.WARNING, "UnsopportedEncodingException"));
        }
    }

}