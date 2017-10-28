import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Comparable<Student>, Serializable{
    String name;
    int groupNumber;
    ArrayList<Integer> marks;

    public Student(String name, int groupNumber, ArrayList<Integer> marks) {
        this.name = name;
        this.groupNumber = groupNumber;
        this.marks = marks;
    }

    public double getAverageMark(){
        double summa = 0;
        for (int mark: marks)
            summa += mark;
        return summa/marks.size();
    }

    @Override
    public int compareTo(Student student) {
        return name.compareTo(student.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", groupNumber=" + groupNumber +
                ", marks=" + marks +
                '}';
    }
}
