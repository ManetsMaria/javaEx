import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Matcher;

public class FileWorker implements ActionListener {

    private MainFrame mainFrame;
    private File currentFile;
    private final int kPortNumber = 8013;
    private final String adress = "localhost";

    public FileWorker(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            JMenuItem menuItem = (JMenuItem) actionEvent.getSource();
            String menuItemType = menuItem.getText();
            switch (menuItemType){
                case "Open":
                    open();
                    break;
                case "Save":
                    save();
                    break;
                case "Decode file":
                        decode();
                        break;
            }
        }
        catch (ClassCastException e) {
            Logger.getLogger(MainFrame.class.getName()).log(new LogRecord(Level.WARNING, "Unknow type"));
        }
    }

    protected void decode(String key){
            try {
                Socket socket = new Socket(adress, kPortNumber);
                System.out.println("Client connected to socket");
                mainFrame.getExecuteIt().execute(new ServerSenter(mainFrame.getTextArea().getText(), key, socket, mainFrame));
                currentFile = null;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    private void decode(){
        String s = (String)JOptionPane.showInputDialog(
                mainFrame, "Create your decode key",
                "I need your key",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "input here");
        if (s != null && !(s.equals(""))) {
            decode (s);
        }
        else{
            JOptionPane.showMessageDialog(null, "try again");
        }
    }

    public void open(File file){
        try {
                currentFile = file;
                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                byte [] buffer = new byte[32768];
                while (true){
                    int readBytesCount = fileInputStream.read(buffer);
                    if (readBytesCount == -1)
                        break;
                }
                String str = new String(buffer);
                mainFrame.getTextArea().setText(str);
                mainFrame.stayNothingHadChanged();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void open(){
        boolean wantToOpen = true;
        if (mainFrame.isAnythingChange()){
            wantToOpen = askToContinue();
        }
        if (wantToOpen) {
            try {
                JFileChooser fileOpen = new JFileChooser();
                TextFileFilter textFileFilter = new TextFileFilter();
                fileOpen.setFileFilter(textFileFilter);
                int ret = fileOpen.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    textFileFilter.exceptionChecker(file);
                    currentFile = file;
                    FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                    byte [] buffer = new byte[32768];
                    while (true){
                        int readBytesCount = fileInputStream.read(buffer);
                        if (readBytesCount == -1)
                            break;
                    }
                    String str = new String(buffer);
                    mainFrame.getTextArea().setText(str);
                    mainFrame.stayNothingHadChanged();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e);
            } catch (NotTextFileException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void save(){
        try {
            File fileName = null;
            if (currentFile == null) {
                JFileChooser fileSave = new JFileChooser();
                TextFileFilter textFileFilter = new TextFileFilter();
                fileSave.setFileFilter(textFileFilter);
                int ret = fileSave.showDialog(null, "Save file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    fileName = new File(fileSave.getSelectedFile() + ".txt");
                    FileWriter writer = new FileWriter(fileName);
                    writer.write(mainFrame.getTextArea().getText());
                    currentFile = fileName;
                    mainFrame.stayNothingHadChanged();
                }
            }
            else{
                FileWriter writer = new FileWriter(fileName);
                writer.write(mainFrame.getTextArea().getText());
                JOptionPane.showMessageDialog(null, "Saved in the same place! :)");
                mainFrame.stayNothingHadChanged();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public boolean askToContinue(){
        Object[] options = {"Yes, go without saving",
                "No, save it please",
                "Cancel"};
        int n = JOptionPane.showOptionDialog(mainFrame,
                "You made some changes. If you exit this file now, you'll loose all your progress. Do you want to continue anyway?",
                "Warning!",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        switch (n){
            case JOptionPane.OK_OPTION:
                return true;
            case JOptionPane.NO_OPTION:
                save();
                return false;
            case JOptionPane.CANCEL_OPTION:
                return false;
        }
        return false;
    }
}

class ServerSenter implements Runnable{
    String keyWord;
    String text;
    Socket socket;
    MainFrame frame;
    public ServerSenter(String text, String keyWord, Socket socket, MainFrame frame){
        this.text = text;
        this.keyWord = keyWord;
        this.socket = socket;
        this.frame = frame;
    }

    @Override
    public void run() {
        try {
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream()); DataInputStream ois = new DataInputStream(socket.getInputStream());
            System.out.println("Client oos & ois initialized");
            oos.writeUTF(text);
            oos.writeUTF(keyWord);
            oos.flush();
            System.out.println("Client wrote & start waiting for data from server...");
            Thread.sleep(10);
            System.out.println("reading...");
            String in = ois.readUTF();
            frame.getTextArea().setText(in);
            frame.noteChange();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        }
    }
