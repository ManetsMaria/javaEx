import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class FileWorker implements ActionListener {

    private MainFrame mainFrame;
    private File currentFile;

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
                case "Encode file":
                        encode();break;
                case "Decode file":
                        decode();
                        break;
            }
        }
        catch (ClassCastException e) {
            Logger.getLogger(MainFrame.class.getName()).log(new LogRecord(Level.WARNING, "Unknow type"));
        }
    }

    private void encode(){
        String s = (String)JOptionPane.showInputDialog(
                mainFrame, "Create your encode key",
                "I need your key",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "input here");
        if (s != null && !(s.equals(""))) {
            byte[] result = EncodingAndDecoding.encode(mainFrame.getTextArea().getText(), s);
            mainFrame.getTextArea().setText(new String(result));
            mainFrame.noteChange();
            currentFile = null;
        }
        else{
            JOptionPane.showMessageDialog(null, "try again");
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
            String result = EncodingAndDecoding.decode(mainFrame.getTextArea().getText().getBytes(), s);
            mainFrame.getTextArea().setText(result);
            mainFrame.noteChange();
            currentFile = null;
        }
        else{
            JOptionPane.showMessageDialog(null, "try again");
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
                   // BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
                    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                    //mainFrame.getTextArea().write(outFile);
                    fileOutputStream.write(mainFrame.getTextArea().getText().getBytes(),0,mainFrame.getTextArea().getText().getBytes().length);
                    currentFile = fileName;
                    mainFrame.stayNothingHadChanged();
                }
            }
            else{
                //BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
                FileOutputStream fileOutputStream = new FileOutputStream(currentFile);
               // mainFrame.getTextArea().write(outFile);
                fileOutputStream.write(mainFrame.getTextArea().getText().getBytes());
                //mainFrame.getTextArea().write(new BufferedWriter(new FileWriter(currentFile)));
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
