package com.qxy.student.service;
import com.qxy.student.dao.StudentDao;
import com.qxy.student.entity.Student;
import com.qxy.student.exception.StudentException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.ToString;//生成ToString方法

import javax.management.openmbean.CompositeDataSupport;
import java.io.Serializable;//实现序列化接口
import java.util.ArrayList;
import java.util.List;

@Data //自动添加getter与setter
@AllArgsConstructor //所有参数的构造
//@NoArgsConstructor //空构造，默认构造
@Accessors(chain = true) //链式编程
@ToString //生成ToString方法

/**
 * 业务逻辑层（service）
 * 主要是进行：校验规则、算总分、重排序号、排序、统计。
 */
public class StudentService implements Serializable {
    /** 依赖Dao() */
    private StudentDao studentDao = new StudentDao();
    /** 无参构造，加载数据 */
    public StudentService(){
        studentDao.load();
    }

    //基础查询
    public int count(){
        return studentDao.count();
    }
    public boolean isFirstStart(){
        return studentDao.isFirstStart();
    }
    public ArrayList<Student> getAll(){
        return studentDao.getAll();
    }
    //根据id查询
    public Student queryById(String id){
        return studentDao.queryById(id);
    }

    /**
     * 需要实现的八个业务逻辑
     *
     */
    //1.添加学生,接收学生信息
    public void add (String id, String name, String classId, double javaScore, double vueScore, double htmlScore) {
        //判断是否输入正确
        if (id == null || name == null || classId == null || javaScore < 0 || vueScore < 0 || htmlScore < 0) {
            throw new StudentException("输入有误，请重新输入");
        }
        //学号重复
        if (studentDao.queryById(id) != null) {
            throw new StudentException("学号重复，请重新输入");
        }
        // 验证分数
        if (javaScore > 100 || vueScore > 100 || htmlScore > 100) {
            throw new StudentException("分数不能超过100，请重新输入");
        }

        Student student = new Student(id, name, classId, javaScore, vueScore, htmlScore);
        studentDao.add(student);//添加学生
        renumber();//序号重排
        studentDao.save();//保存数据
        System.out.println("添加成功");

    }
        //2.删除学生
        public void delete(String id) {
            Student student = studentDao.queryById(id);
            if(student == null){
                throw new StudentException("删除失败，学号不存在");
            }
            studentDao.remove(student);
            //序号重排
            renumber();
            studentDao.save();//保存数据
            System.out.println("删除成功");
        }
        //3.修改学生成绩
        public void updateScore(String id, double javaScore, double vueScore, double htmlScore){
            //用s记录学生ID
            Student s = studentDao.queryById(id);
            if(s == null){
                throw new StudentException("修改失败，学号不存在");
            }
            //成绩不为-1时才修改
            if(javaScore != -1){
                if(javaScore < 0 || javaScore > 100){
                    throw new StudentException("分数不能超过100，请重新输入");
                }
                s.setJavaScore(javaScore);
            }
            if(vueScore != -1){
                if(vueScore < 0 || vueScore > 100){
                    throw new StudentException("分数不能超过100，请重新输入");
                }
                s.setVueScore(vueScore);
            }
            if(htmlScore != -1){
                if(htmlScore < 0 || htmlScore > 100){
                    throw new StudentException("分数不能超过100，请重新输入");
                }
                s.setHtmlScore(htmlScore);
            }
            //计算总分
            s.setTotalScore(s.getJavaScore() + s.getVueScore() + s.getHtmlScore());
            studentDao.update(id, s);
            studentDao.save();//保存数据
        }

        //4.按学号查询学生信息
        public Student queryByid(String id){
            return studentDao.queryById(id);
        }
        //5.显示所有学生信息
        public ArrayList<Student> showAll(){
            return studentDao.getAll();
        }
        //6.按总分排序，显示top10
        public List<Student> showTop10(){
            studentDao.sortDesc();
            if(studentDao.getAll().size() > 10){
                return studentDao.getAll().subList(0, 10);
            }else{
                System.out.println("数据不足10条，显示所有数据");
                return studentDao.getAll().subList(0, Math.min(10, studentDao.getAll().size()));
            }

        }
        //7.显示某班的全部学生信息
        public ArrayList<Student> queryByClassId(String classId) {
            ArrayList<Student> result = new ArrayList<>();
            for (Student student : studentDao.getAll()) {
                if (student.getClassId().equals(classId)) {
                    result.add(student);
                }
            }
            return result;
        }
        //8.退出系统
        public void exit() {
            System.out.println("退出系统");
            System.exit(0);
        }






    //序号重排，保证删除后序号连续
    public void renumber() {
        //List和ArrayList的区别：List是接口，ArrayList是List接口的实现类

        List<Student> students = studentDao.getAll();
        for (int i = 0; i < students.size(); i++) {
            students.get(i).setIndex(i + 1);
        }
    }
}