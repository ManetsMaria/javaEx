/**
 * Created by mariya on 14.2.17.
 */
import java.util.HashSet;
import java.util.Iterator;
public class ProgrammingTest {
    public static void main(String args[]){
        HashSet <Student> students=new HashSet <Student> ();
        students.add(new Undergraduate("Misha","misha1", "misha1@gmail.com", new Academic("Aleksandr")));
        students.add(new Undergraduate("Pasha","pasha1", "pasha@gmail.com", new Academic("Aleksandr")));
        students.add(new Postgraduate("Misha","misha2", "misha2@gmail.com", new Academic("Aleksandr")));
        students.add(new Postgraduate("Lesha","lesha1", "lesha@gmail.com", new Academic("Aleksandr")));
        students.add(new Postgraduate("Katy","katy1", "katy@gmail.com", new Academic("Pavel")));
        Course course=new Course("course1", students);
        HashSet<Postgraduate> post=course.getPostgraduates("Aleksandr");
        Iterator <Postgraduate> i=post.iterator();
        while(i.hasNext()){
            System.out.println(i.next().getEmail());
        }
        Notifier <Postgraduate>   note=new Notifier(post);
        note.doNotifyAll();
    }
}
