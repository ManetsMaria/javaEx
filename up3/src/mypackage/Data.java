package mypackage;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;

public class Data {
    public static List<Student> loadStudents() throws FileNotFoundException{
        List<Student> students=new ArrayList<>();
        Scanner scanner = new Scanner(new File("students.txt"));
        try{
        while(scanner.hasNext())
            students.add(new Student(scanner.nextLine()));
        }catch (Exception ex){

        }
        return students;
    }
    public static TreeNode buildTree(List<Student> students){
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("BSU");
        Collections.sort(students);
        for(Student x : students){
            DefaultMutableTreeNode rnode=root;
            DefaultMutableTreeNode node;

            node=getChild(rnode,x.getFaculty());
            if(node==null){
                node=new DefaultMutableTreeNode(x.getFaculty());
                rnode.add(node);
            }
            rnode=node;

            node=getChild(rnode,x.getCourse());
            if(node==null){
                node=new DefaultMutableTreeNode(x.getCourse());
                rnode.add(node);
            }
            rnode=node;

            node=getChild(rnode,x.getGroup());
            if(node==null){
                node=new DefaultMutableTreeNode(x.getGroup());
                rnode.add(node);
            }
            rnode=node;

            node=getChild(rnode,x.getName());
            if(node==null){
                node=new DefaultMutableTreeNode(x);
                rnode.add(node);
            }
            rnode=node;
        }
        return root;
    }
    private static DefaultMutableTreeNode getChild(DefaultMutableTreeNode root, Object tag){
        Enumeration<DefaultMutableTreeNode> enumeration=root.children();
        while(enumeration.hasMoreElements()){
            DefaultMutableTreeNode node=enumeration.nextElement();
            if(node.getUserObject().equals(tag)) return node;
        }
        return null;
    }
}
