package mypackage;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class MainWindow extends JFrame {
    List<Student> students=new ArrayList<>();
    JTree tree;
    MouseAdapter mouseAdapter;
    ImageIcon rootIcon;
    ImageIcon nodeIcon;
    ImageIcon leafIcon;
    public MainWindow(){
        super("Students");
        rootIcon = new ImageIcon("img/BSU.jpg");
        nodeIcon = new ImageIcon("img/fpmi.jpg");
        leafIcon = new ImageIcon("img/student.jpg");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        try{
            students=Data.loadStudents();
        }catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Файл не найден!");
        }
        mouseAdapter=new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                    if (node == null) return;
                    if (!node.isLeaf()) return;
                    System.out.println(node);
                    Student oldSt=(Student)node.getUserObject();
                    String str=getString(oldSt.toString());
                    if(str==null) return;
                    if(str.equals("")){
                        students.remove(oldSt);
                        showAll();
                        return;
                    }
                    try{
                        Student newSt=new Student(str);
                        students.remove(oldSt);
                        if(!students.contains(newSt))
                            students.add(newSt);
                        showAll();
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Не правильный ввод");
                        mouseClicked(e);
                    }
                }
            }
        };
        tree=new JTree();
        tree.addMouseListener(mouseAdapter);

        JButton button=new JButton("Добавить");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str=getString("fpmi 1 1 poiu");
                if(str==null) return;
                try{
                    Student new_st=new Student(str);
                    if(!students.contains(new_st)){
                        students.add(new_st);
                        showAll();
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Не правильный ввод");
                    actionPerformed(e);
                }
            }
        });
        add(new JScrollPane(tree), BorderLayout.CENTER);
        MyRenderer rend=new MyRenderer();
        rend.setLeafIcon(leafIcon);
        tree.setCellRenderer(rend);
        add(button,BorderLayout.SOUTH);
        showAll();
    }
    private void showAll(){
        tree.setModel(new DefaultTreeModel(Data.buildTree(students)));
        expandAllNodes(tree, 0, tree.getRowCount());

    }
    private class MyRenderer extends DefaultTreeCellRenderer{
        public MyRenderer(){
        }
        @Override
        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value, boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (tree.getModel().getRoot().equals(node)) {
                setIcon(rootIcon);
            } else if (tree.getModel().getChild(tree.getModel().getRoot(), 0).equals(node)) {
                setIcon(nodeIcon);
            } else if (node.getChildCount()==0){
                setIcon(leafIcon);
            }
            return this;
        }
    }
    private String getString(String init){
        return (String) JOptionPane.showInputDialog(
                this,
                "Данные студента:\nФакультет Курс Группа ФИО",
                "Редактирование студента Введите пустую стоку что бы удалить",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                init);
    }
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
        for(int i=startingIndex;i<rowCount;++i){
            tree.expandRow(i);
        }

        if(tree.getRowCount()!=rowCount){
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

}
