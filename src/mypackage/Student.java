package mypackage;
import java.util.Collections;
import java.lang.Number;
public class Student extends Number implements Comparable <Student> {
    int group;
    double ball;
    String name;
    public Student(int g, double b, String n){
        group=g;
        ball=b;
        name=n;
    }
    public int compareTo(Student o) {
        int result;
        result = Integer.compare(group, o.group);
        if(result != 0) 
            return result;
        result = Double.compare(ball, o.ball);
        if(result != 0) 
            return result;
        return name.compareTo(o.name);
    }
    public String toString(){
        StringBuffer s=new StringBuffer();
        s.append(Integer.toString(group));
        s.append(", ");
        s.append(Double.toString(ball));
        s.append(", ");
        s.append(name);
        s.append(";");
        return s.toString();
    }
    @Override
    public double doubleValue(){
        return ball;
    }
    ///
    @Override
    public float floatValue(){
        return (float)ball;
    }
    @Override
    public  long longValue(){
        return (long)group;
    }
    @Override
    public int intValue(){
        return group;
    }
}
