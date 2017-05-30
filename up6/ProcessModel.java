import java.time.LocalDate;


public class ProcessModel {
    static public LocalDate processDateOperandNumber(LocalDate date, int num, String op){
        if (op.equals("+")){
            return date.plusDays(num);
        }
        else return date.minusDays(num);
    }
}
