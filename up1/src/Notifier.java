/**
 * Created by mariya on 14.2.17.
 */
import java.util.HashSet;
import java.util.Iterator;
public class Notifier <T extends Notifiable> {
    private HashSet <T> students=new HashSet <T>();
    public Notifier(HashSet <T> s){
        students=s;
    }
    public void doNotifyAll(){
        Iterator <T> iter=students.iterator();
        while (iter.hasNext()){
            iter.next().notify("Called");
        }
    }
}
