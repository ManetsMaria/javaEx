package mypackage;

import java.util.Scanner;

public class Student implements Comparable <Student>{
    private String faculty;
    private int course;
    private int group;
    private String name;

    public Student(String faculty, int course, int group, String name) {
        this.faculty = faculty;
        this.course = course;
        this.group = group;
        this.name = name;
    }

    public Student(String string)throws NumberFormatException{
        Scanner scan=new Scanner(string);
        faculty=scan.next();
        course=scan.nextInt();
        group=scan.nextInt();
        name=scan.nextLine().trim();
    }
    public int getGroup(){
        return group;
    }
    public String getFaculty(){
        return faculty;
    }
    public String getName(){
        return name;
    }
    public int getCourse(){
        return course;
    }
    @Override
    public String toString(){
        StringBuffer s=new StringBuffer();
        s.append(faculty);
        s.append(" ");
        s.append(course);
        s.append(" ");
        s.append(group);
        s.append(" ");
        s.append(name);
        return s.toString();
    }
    @Override
    public int compareTo(Student t) {
        int result;
        result = faculty.compareTo(t.faculty);
        if(result != 0) 
            return result;
        result = course-t.course;
        if (result!=0)
            return result;
        result = group-t.group;
        return result;
    }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Student)) return false;
        Student other=(Student) obj;
        return other.faculty.equals(faculty) && other.name.equals(name) && other.course==course && other.group==group;
    }
}
