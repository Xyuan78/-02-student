package com.qxy.student.ui;

import com.qxy.student.entity.Student;
import com.qxy.student.exception.StudentException;
import com.qxy.student.service.StudentService;

import java.util.List;
import java.util.Scanner;

/**
 * 界面层（ui）
 * 负责菜单显示、控制台输入输出，业务处理全部交给 service。
 */
public class StudentMyFace {
    private static final StudentService studentService = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("欢迎使用学生管理系统");
        if (studentService.isFirstStart()) {
            System.out.println("检测到首次启动，请手动添加学生信息。");
        } else {
            System.out.println("已从序列化文件加载历史数据，共 " + studentService.count() + " 条记录。");
        }

        while (true) {
            printMenu();
            int choice = readInt("请输入你的选择：");
            try {
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        deleteStudent();
                        break;
                    case 3:
                        updateStudent();
                        break;
                    case 4:
                        queryStudent();
                        break;
                    case 5:
                        showAllStudent();
                        break;
                    case 6:
                        showTop10Student();
                        break;
                    case 7:
                        queryByClassStudent();
                        break;
                    case 8:
                        studentService.exit();
                        return;
                    default:
                        System.out.println("无效的选择，请重新输入");
                }
            } catch (StudentException e) {
                System.out.println("操作失败：" + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("----------------------------------------");
        System.out.println("1. 添加学生信息");
        System.out.println("2. 删除学生信息");
        System.out.println("3. 修改学生信息");
        System.out.println("4. 查询学生信息");
        System.out.println("5. 显示所有学生信息");
        System.out.println("6. 显示前10名学生信息");
        System.out.println("7. 按班级查询学生信息");
        System.out.println("8. 退出系统");
        System.out.println("----------------------------------------");
    }

    //1.添加学生信息
    private static void addStudent() {
        System.out.println("添加学生信息");
        String id = readString("请输入学号：");
        String name = readString("请输入姓名：");
        String classId = readString("请输入班级编号：");
        double javaScore = readDouble("请输入Java成绩：");
        double vueScore = readDouble("请输入Vue成绩：");
        double htmlScore = readDouble("请输入HTML成绩：");
        studentService.add(id, name, classId, javaScore, vueScore, htmlScore);
    }

    //2.删除学生信息
    private static void deleteStudent() {
        System.out.println("删除学生信息");
        String id = readString("请输入要删除的学号：");
        studentService.delete(id);
    }

    //3.修改学生信息
    private static void updateStudent() {
        System.out.println("修改学生信息（成绩不修改请输入0）");
        String id = readString("请输入要修改的学号：");
        double javaScore = readDouble("请输入Java成绩：");
        double vueScore = readDouble("请输入Vue成绩：");
        double htmlScore = readDouble("请输入HTML成绩：");
        studentService.updateScore(id, javaScore, vueScore, htmlScore);
    }

    //4.查询学生信息
    private static void queryStudent() {
        System.out.println("查询学生信息");
        String id = readString("请输入要查询的学号：");
        Student student = studentService.queryByid(id);
        if (student == null) {
            System.out.println("未找到该学号的学生信息");
        } else {
            student.showInfo();
        }
    }

    //5.显示所有学生信息
    private static void showAllStudent() {
        System.out.println("显示所有学生信息");
        List<Student> students = studentService.showAll();
        if (students.isEmpty()) {
            System.out.println("暂无学生信息");
            return;
        }
        for (Student student : students) {
            student.showInfo();
        }
    }

    //6.显示前10名学生信息
    private static void showTop10Student() {
        System.out.println("显示前10名学生信息");
        List<Student> top10 = studentService.showTop10();
        if (top10.isEmpty()) {
            System.out.println("暂无学生信息");
            return;
        }
        for (Student student : top10) {
            student.showInfo();
        }
    }

    //7.按班级查询学生信息
    private static void queryByClassStudent() {
        System.out.println("按班级查询学生信息");
        String classId = readString("请输入班级编号：");
        List<Student> students = studentService.queryByClassId(classId);
        if (students.isEmpty()) {
            System.out.println("该班级暂无学生信息");
            return;
        }
        for (Student student : students) {
            student.showInfo();
        }
    }
    //读取字符串
    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    //读取整数，输入非法时提示重输
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("请输入数字！");
            }
        }
    }

    //读取小数，输入非法时提示重输
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("请输入数字！");
            }
        }
    }
}