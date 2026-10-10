package com.qxy.student.dao;


import com.qxy.student.entity.Student;

import java.io.*;
import java.util.ArrayList;

/**
 * 数据访问层（DAO）
 * 职责只有两件事：
 *   1. 用 ArrayList 保存学生数据，并提供最基本的增删改查；
 *   2. 用对象流把 ArrayList 序列化到文件 / 从文件反序列化回来。
 * 这里不做任何业务校验（学号是否重复、成绩是否越界由 service 负责）。
 */
public class StudentDao {
    private ArrayList<Student> students = new ArrayList<>();
    private static final String DATA_FILE = "students.dat";
    //基本的增删改查

    // 新增一条学生记录
    public  void add(Student student){
        students.add(student);
    }
    //删除一条学生记录
    public boolean remove(Student student){
        return students.remove(student);
    }

    /*修改记录
    根据学号修改学生信息
    */
    public boolean update(String id, Student newStudent){
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.set(i, newStudent);
                return true;
            }
        }
        return false;
    }

    //根据学号查询学生信息
    public Student queryById(String id){
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    //返回全部信息
    public ArrayList<Student> getAll(){
        return students;
    }
    //返回学生数量
    public int count(){
        return students.size();
    }

   
    // 总分降序排列
    public void sortDesc(){
        students.sort((s1, s2) -> Double.compare(s2.getTotalScore(), s1.getTotalScore()));
    }


    //序列化与反序列化
   //判断是第一次启动还是之后启动
    public boolean isFirstStart() {
        return !new File(DATA_FILE).exists();
    }
    //序列化：把整个ArrayList保存到文件
    public void save(){
        File file = new File(DATA_FILE);
        File parent = file.getParentFile();//获取父目录
        try{
            if(parent != null && !parent.exists()){
                parent.mkdirs();//创建父目录
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //序列化,使用try-with-resources
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(students);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //反序列化：从文件加载整个ArrayList
    public void load() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                students = (ArrayList<Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("文件不存在，无法加载数据");
        }
    }


}
