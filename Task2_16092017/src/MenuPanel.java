import javax.swing.*;
import java.awt.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class MenuPanel extends JPanel {

    FileWorker fileWorker;

    public MenuPanel(FileWorker ownerFrameFileWorker){
        this.fileWorker = ownerFrameFileWorker;
        this.setLayout(new GridLayout(1, 4));
        this.setBackground(Color.black);
        JMenuBar menuBar = new JMenuBar();
        this.add(menuBar);
        this.setBorder(BorderFactory.createEtchedBorder());
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        createMenuItem(fileMenu,"Save", new ImageIcon("image/save.png"));
        createMenuItem(fileMenu, "Open", new ImageIcon("image/open.png"));
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        createMenuItem(editMenu,"Add new student", new ImageIcon("image/add.png"));
    }
    private void createMenuItem (JMenu highMenu, String name, ImageIcon icon){
        JMenuItem menuItem = new JMenuItem(name, icon);
        menuItem.addActionListener(fileWorker);
        highMenu.add(menuItem);
    }
}
