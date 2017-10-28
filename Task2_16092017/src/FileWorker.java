import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
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
                case "Add new student":
                    addStudent();
                    break;
            }
        }
        catch (ClassCastException e) {
            Logger.getLogger(MainFrame.class.getName()).log(new LogRecord(Level.WARNING, "Unknow type"));
        }
    }

    private void addStudent(){
        //System.out.println("here");
        String input = JOptionPane.showInputDialog(mainFrame, "Input: name group marks...");
        if (input == null)
            return;
        Scanner scanner = new Scanner(input);
        //System.out.println("here1");
        try{
            String name = scanner.next();
            //System.out.println("here2");
            int group = scanner.nextInt();
            //System.out.println("here3");
            ArrayList <Integer> marks = new ArrayList<>();
            while(scanner.hasNext())
                marks.add(scanner.nextInt());
            if (marks.isEmpty())
                JOptionPane.showMessageDialog(null, "incorrect input");
            else {
                Student student = new Student(name, group, marks);
                //System.out.println(input);
                if (mainFrame.getCourse().addStudent(student)) {
                    mainFrame.getOriginal().add(student);
                    //System.out.println("here5");
                    mainFrame.showAll();
                    mainFrame.noteChange();
                    //System.out.println(mainFrame.getOriginal());
                    //System.out.println(mainFrame.getCourse());
                }
                else{
                    JOptionPane.showMessageDialog(null, "here yet");
                }
            }
        }
        catch (InputMismatchException e){
            JOptionPane.showMessageDialog(null, e);
        }
        catch (NoSuchElementException e){
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
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream oin = new ObjectInputStream(fis);
                    ArrayList <Student> students = mainFrame.getOriginal();
                    while (true) {
                        try {
                            students.add((Student) oin.readObject());
                        } catch (EOFException e) {
                            break;
                        }
                    }
                    fis.close();
                    for (Student student: mainFrame.getOriginal())
                        mainFrame.getCourse().addStudent(student);
                    mainFrame.showAll();
                    mainFrame.stayNothingHadChanged();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e);
            } catch (NotTextFileException e) {
                JOptionPane.showMessageDialog(null, e);
            } catch (ClassNotFoundException e){
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
                    fileName = new File(fileSave.getSelectedFile() + ".students");
                    FileOutputStream fos = new FileOutputStream(fileName);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    for (Student student: mainFrame.getOriginal())
                        oos.writeObject(student);
                    oos.flush();
                    oos.close();
                    currentFile = fileName;
                    mainFrame.stayNothingHadChanged();
                }
            }
            else{
                FileOutputStream fos = new FileOutputStream(currentFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Student student: mainFrame.getOriginal())
                    oos.writeObject(student);
                oos.flush();
                oos.close();
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
