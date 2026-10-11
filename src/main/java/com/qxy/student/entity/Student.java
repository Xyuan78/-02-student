package com.qxy.student.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.ToString;//生成ToString方法
import java.io.Serializable;//实现序列化接口

@Data //自动添加getter与setter
@AllArgsConstructor //所有参数的构造
@NoArgsConstructor //空构造，默认构造
@Accessors(chain = true) //链式编程
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    //属性，方法

    //序号
    private int index;
    //学号
    private String id;
    //姓名
    private String name;
    //班级编号
    private String classId;
    //java成绩
    private double javaScore;
    //Vue成绩
    private double vueScore;
    //HTML成绩
    private double htmlScore;
    //总分
    private double totalScore;

    public Student(String id, String name, String classId, double javaScore, double vueScore, double htmlScore) {
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.javaScore = javaScore;
        this.vueScore = vueScore;
        this.htmlScore = htmlScore;
        // 总分在创建时直接计算
        this.totalScore = javaScore + vueScore + htmlScore;
    }

    //打印一个学生的信息
    public void showInfo() {
        System.out.println("----------------------------------------");
        System.out.print("序号：" + index + "\t");
        System.out.print("学号：" + id + "\t");
        System.out.print("姓名：" + name + "\t");
        System.out.print("班级编号： " + classId + "\t");
        System.out.print("Java成绩：" + javaScore + "\t");
        System.out.print("Vue成绩：" + vueScore + "\t");
        System.out.print("HTML成绩：" + htmlScore + "\t");
        System.out.println("总分：" + totalScore + "\t");
    }

}
