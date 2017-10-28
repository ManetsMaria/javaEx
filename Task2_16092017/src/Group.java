import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

public class Group implements Comparable<Group> {
    int groupNumber;
    TreeSet<Student> students;

    public Group(int groupNumber) {
        this.groupNumber = groupNumber;
        students = new TreeSet<>();
    }

    public boolean addNewStudent(Student student){
        if (students.contains(student))
            return false;
        students.add(student);
        return true;
    }

    public double getAverageGroup(){
        double summa = 0;
        for (Student student: students){
            summa += student.getAverageMark();
        }
        return summa/students.size();
    }

    public TreeSet<Student> getStudents() {
        return students;
    }

    @Override
    public int compareTo(Group group) {
        return Double.compare(group.getAverageGroup(),getAverageGroup());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return groupNumber == group.groupNumber;
    }

    @Override
    public int hashCode() {
        return groupNumber;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupNumber=" + groupNumber +
                ", students=" + students +
                '}';
    }
}
