import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.TreeSet;

public class Course{
    ArrayList <Group> groups;

    public Course(){
        groups = new ArrayList<>();
    }

    public boolean addStudent(Student student){
        int groupNumber = student.groupNumber;
        try{
            Group group = groups.stream().filter(groupFinder -> Objects.equals(groupFinder, new Group(groupNumber))).findFirst().get();
            return group.addNewStudent(student);
        }
        catch (NoSuchElementException e){
            Group group = new Group(groupNumber);
            boolean flag = group.addNewStudent(student);
            groups.add(group);
            return flag;
        }
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        return "Course{" +
                "groups=" + groups +
                '}';
    }
}
