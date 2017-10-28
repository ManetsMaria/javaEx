import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class DataAndCurrencyPanel extends JPanel {
    DateFormat dateFormat;
    NumberFormat numberFormat;
    JLabel currency;
    JLabel dates;
    public DataAndCurrencyPanel(Locale locale){
        currency = new JLabel();
        dates = new JLabel();
        this.setLayout(new GridLayout(1, 2));
        this.add(currency);
        this.add(dates);
        change(locale);
    }
    public void change(Locale locale){
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
        numberFormat = NumberFormat.getCurrencyInstance(locale);
        dates.setText(dateFormat.format(new Date(System.currentTimeMillis())));
        currency.setText(numberFormat.format(1.2));
    }

}
