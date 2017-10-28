import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
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
    private Course course;
    private ArrayList<Student> original;


    public MainFrame() {
        super();
        initialize();
    }

    private void initialize() {
        fileWorker = new FileWorker(this);
        textArea = new JTextArea();
        isFileChanged = false;
        initSystemLookAndFeel();
        this.setSize(500, 500);
        this.add(new MenuPanel(fileWorker), BorderLayout.NORTH);
        this.add(new JScrollPane(textArea));
        textArea.setEditable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Course show Window");
        this.addWindowListener(this);
        course = new Course();
        original = new ArrayList<>();
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

    public Course getCourse(){
        return course;
    }

    public ArrayList<Student> getOriginal() {
        return original;
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
    public void showAll(){
        textArea.setText("");
        Collections.sort(course.getGroups());
        for (Group group: course.getGroups()){
            textArea.append("Group "+group.groupNumber+":\n");
            for (Student student: group.getStudents())
                textArea.append(student.name+" "+student.getAverageMark()+"\n");
            textArea.append("average mark: "+group.getAverageGroup()+"\n");
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
}