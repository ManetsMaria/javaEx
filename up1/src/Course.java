/**
 * Created by mariya on 13.2.17.
 */
import java.util.HashSet;
import java.util.Iterator;
public class Course {
    private HashSet <Student> students=new HashSet <Student>();
    private String name;
    public Course(String n, HashSet <Student> s){
        name=n;
        students=s;
    }
    HashSet <Postgraduate> getPostgraduates(String nameOfSupervisor){
        Iterator <Student> iter=students.iterator();
        HashSet <Postgraduate> answer=new HashSet<Postgraduate>();
        while(iter.hasNext()){
            Student s=iter.next();
            Postgraduate tester=new Postgraduate("a","b","c", new Academic("p"));
           if (tester.getClass() == s.getClass()){
               Postgraduate p= (Postgraduate) s;
               if (p.getSupervisor().getName().compareTo(nameOfSupervisor)==0)
                   answer.add(p);
           }
        }
        return answer;
    }
}
