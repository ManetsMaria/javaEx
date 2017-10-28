import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class MenuPanel extends JPanel {
    private MenuPanelStrings menuPanelStrings;
    private MainFrame mainFrame;
    private JMenu fileMenu;
    private JMenuItem open;
    private JMenuItem eng;
    private JMenuItem ru;
    private JMenu editMenu;

    public MenuPanel(MainFrame frame){
        mainFrame = frame;
        menuPanelStrings = new MenuPanelStrings(mainFrame.getCurrentLocale());
        createMenuPanel();
    }
    private JMenuItem createMenuItem (JMenu highMenu, String name, ImageIcon icon){
        JMenuItem menuItem = new JMenuItem(name, icon);
        highMenu.add(menuItem);
        return  menuItem;
    }
    private void createMenuPanel(){
        this.setLayout(new GridLayout(1, 4));
        this.setBackground(Color.black);
        JMenuBar menuBar = new JMenuBar();
        this.add(menuBar);
        this.setBorder(BorderFactory.createEtchedBorder());
        fileMenu = new JMenu(menuPanelStrings.getFile());
        menuBar.add(fileMenu);
        open = createMenuItem(fileMenu, menuPanelStrings.getOpen(), new ImageIcon("image/open.png"));
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               loadClass();
            }
        });
        editMenu = new JMenu(menuPanelStrings.getChangeLanguage());
        menuBar.add(editMenu);
        eng = createMenuItem(editMenu,menuPanelStrings.getEnglish(), new ImageIcon("image/eng.png"));
        eng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setCurrentLocale(Locale.UK);
            }
        });
        ru = createMenuItem(editMenu, menuPanelStrings.getRussian(), new ImageIcon("image/ru.png"));
        ru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.setCurrentLocale(new Locale("ru", "RU"));
            }
        });
    }
    public void changeLocale(Locale locale){
        menuPanelStrings = new MenuPanelStrings(locale);
        fileMenu.setText(menuPanelStrings.getFile());
        open.setText(menuPanelStrings.getOpen());
        eng.setText(menuPanelStrings.getEnglish());
        ru.setText(menuPanelStrings.getRussian());
        editMenu.setText(menuPanelStrings.getChangeLanguage());
    }
    public void loadClass(){
        try{
            ResourceBundle rb = ResourceBundle.getBundle("properties/words", mainFrame.getCurrentLocale());
            String st = rb.getString("Dialog");
            String dialog = new String(st.getBytes("ISO-8859-1"), "UTF-8");
            st = rb.getString("Ok");
            String ok = new String(st.getBytes("ISO-8859-1"), "UTF-8");
            st = rb.getString("Cancel");
            String cancel = new String(st.getBytes("ISO-8859-1"), "UTF-8");
            Object[] options = new Object[2];
            options[0] = ok;
            options[1] = cancel;
            //Locale.setDefault(mainFrame.getCurrentLocale());
            String input = JOptionPane.showInputDialog(mainFrame, dialog, ok, 3);
            if (input == null || input.equals(""))
                return;
            String[] splitInput = input.split(" ");
            String name = splitInput[0];
            String[] args = new String[splitInput.length - 1];
            int i = 0;
            for(String x: splitInput){
                if (!x.equals(name)){
                    args[i] = x;
                    i++;
                }
            }
            /*JFileChooser fileOpen = new JFileChooser();
            TextFileFilter textFileFilter = new TextFileFilter();
            fileOpen.setFileFilter(textFileFilter);
            int ret = fileOpen.showDialog(null, menuPanelStrings.getChoose());
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                textFileFilter.exceptionChecker(file);
                String className = file.getName().split("\\.")[0];
                ClassLoader cl = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
                Class c = cl.loadClass(className);
                Method m = c.getMethod("firstGroup", null);
                m.invoke(null, null);*/
                ClassLoader cl = URLClassLoader.newInstance(new URL[]{new File(".").toURI().toURL()});
                Class c = cl.loadClass(name);
                Method m = c.getMethod("firstGroup", String[].class);
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try{
                            m.invoke(null, (Object) args);
                        }
                        catch (InvocationTargetException e){
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, e);
                        }catch (IllegalAccessException e){
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                });
            }catch (UnsupportedEncodingException e){
            JOptionPane.showMessageDialog(null, e);
        }/*catch (NotTextFileException e) {
            JOptionPane.showMessageDialog(null, e);}*/
        catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e);
        }catch (MalformedURLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        catch (NoSuchMethodException e){
            JOptionPane.showMessageDialog(null, e);
        }

    }
}
