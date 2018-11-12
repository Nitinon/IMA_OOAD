package classss;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.Serializable;
import java.util.List;

@Entity
public class Subject implements Serializable{
    // private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long id_sub;
    private String name;
    private int no_student;
    private String time;
    private int section;
    private String description;

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    @ManyToOne private Teacher teacher;

    private List<Student> student = new ArrayList<Student>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Subject(String name, int no_student, String time, int section, String description) {
        this.name = name;
        this.no_student = no_student;
        this.time = time;
        this.section = section;
        this.description = description;
    }

    public String getDiscription() {
        return description;
    }

    public void setDiscription(String discription) {
        this.description = discription;
    }

    public void addStudent(Student student){
        this.student.add(student);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public long getId_sub() {
        return id_sub;
    }

    public void setId_sub(long id_sub) {
        this.id_sub = id_sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo_student() {
        return no_student;
    }

    public void setNo_student(int no_student) {
        this.no_student = no_student;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }


}
