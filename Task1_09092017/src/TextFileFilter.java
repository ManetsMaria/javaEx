import java.io.File;

import javax.swing.filechooser.FileFilter;

class NotTextFileException extends Exception{

    public NotTextFileException(String message){
        super(message);
    }
}

public class TextFileFilter extends FileFilter {

    public boolean accept(File pathname) {
        String name = pathname.getName().toLowerCase();
        return pathname.isDirectory() || name.endsWith(".txt");
    }

    public String getDescription() {
        return "Text Files (*.txt)";
    }

    public void exceptionChecker(File file) throws NotTextFileException {
        if (!accept(file)) {
            throw new NotTextFileException("Unknow type");
        }
    }
}