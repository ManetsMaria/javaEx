import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainFrame extends JFrame implements WindowListener {

    private FileWorker fileWorker;
    private JTextArea textArea;
    private boolean isFileChanged;
    private ExecutorService executeIt;

    public MainFrame() {
        super();
        initialize();
    }

    private void initialize() {

        executeIt = Executors.newFixedThreadPool(2);
        fileWorker = new FileWorker(this);
        textArea = new JTextArea();
        isFileChanged = false;
        initSystemLookAndFeel();
        this.setSize(500, 500);
        this.add(new MenuPanel(fileWorker), BorderLayout.NORTH);
        this.add(new JScrollPane(textArea));
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                noteChange();
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                noteChange();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                noteChange();
            }
        });
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Encoder and Decoder Window");
        this.addWindowListener(this);
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

    public JTextArea getTextArea(){
        return textArea;
    }

    public boolean isAnythingChange(){
        return isFileChanged;
    }

    public void stayNothingHadChanged() {
        isFileChanged = false;
    }

    public void noteChange(){
        isFileChanged = true;
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        boolean wantToExit = true;
        if (isAnythingChange()) {
           wantToExit = fileWorker.askToContinue();
        }
        if (wantToExit){
            this.dispose();
            System.exit(0);
        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
    }

    /*public Socket getSocket() {
        return socket;
    }*/

    public ExecutorService getExecuteIt() {
        return executeIt;
    }
    public void fastTest(){
        fileWorker.open(new File("test.txt"));
        fileWorker.decode("input here");
    }
}