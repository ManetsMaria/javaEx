import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MenuPanelStrings {
    private String file;
    private String open;
    private String changeLanguage;
    private String english;
    private String russian;
    private String choose;

    public MenuPanelStrings(Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("properties/words", locale);
        try {
            String st = rb.getString("File");
            file = new String(st.getBytes("ISO-8859-1"), "UTF-8");
            st = rb.getString("Open");
            open = new String(st.getBytes("ISO-8859-1"), "UTF-8");
            st = rb.getString("ChangeLanguage");
            changeLanguage = new String(st.getBytes("ISO-8859-1"), "UTF-8");
            st = rb.getString("English");
            english = new String(st.getBytes("ISO-8859-1"), "UTF-8");
            st = rb.getString("Russian");
            russian = new String(st.getBytes("ISO-8859-1"), "UTF-8");
            st = rb.getString("ChooseProgrammFile");
            choose = new String(st.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger(MenuPanelStrings.class.getName()).log(new LogRecord(Level.WARNING, "UnsopportedEncodingException"));
        }
    }

    public String getFile() {
        return file;
    }

    public String getOpen() {
        return open;
    }

    public String getChangeLanguage() {
        return changeLanguage;
    }

    public String getEnglish() {
        return english;
    }

    public String getRussian() {
        return russian;
    }

    public String getChoose() {
        return choose;
    }
}
