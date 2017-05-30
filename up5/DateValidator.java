import java.text.*;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class DateValidator {
 
    private static final String DATE_PATTERN
            = "^(((0?[1-9]|[12][0-9]|3[0])\\.(0?[1-9]|1[012]))|([3][1]\\.(([1][0])|([1][2])|(0?[13578]))))\\.(\\d\\d\\d\\d)$";
 
    private final Pattern pattern;
    private Matcher matcher;
 
    public DateValidator() {
        pattern = Pattern.compile(DATE_PATTERN);
    }
 
    public boolean validate(String date) {
 
        matcher = pattern.matcher(date);
 
        if (matcher.matches()) {
 
            matcher.reset();
 
            if (matcher.find()) 
                /*{
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));
 
                if ("31".equals(day)) {
                    return Arrays.asList(new String[]{"1", "01", "3", "03",
                        "5", "05", "7", "07", "8", "08", "10", "12"})
                            .contains(month);
 
                } else if ("2".equals(month) || "02".equals(month)) {
                    if (year % 4 == 0) {
                        if (year % 100 == 0) {
                            if (year % 400 == 0) {
                                return Integer.parseInt(day) <= 29;
                            }
                            return Integer.parseInt(day) <= 28;
                        }
                        return Integer.parseInt(day) <= 29;
                    } else {
                        return Integer.parseInt(day) <= 28;
                    }
 
                } else {
                    return true;
                }
            }*/
            return true;
        }
 
        return false;
    }
}

