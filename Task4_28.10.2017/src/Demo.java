import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Demo {
    static public void main(String[] args){
        try {
            FileCorrector.changeKeyWords("input.java", "words.txt");
        }
        catch (IOException e){
            Logger.getLogger(Demo.class.getName()).log(new LogRecord(Level.WARNING, e.getMessage()));
        }
    }
}
