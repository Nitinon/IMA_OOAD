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
    private String day;
    private String description;
    private int studentNum;

    @OneToMany(mappedBy = "subjectsss",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Announcement> announcements = new ArrayList<Announcement>();

    public Subject(String name, int no_student, String time, String day, String description) {
        this.name = name;
        this.no_student = no_student;
        this.time = time;
        this.day = day;
        this.description = description;
        this.studentNum=0;
    }
    public void addAnnouncement(Announcement announcement){
        announcement.setSubjectsss(this);
        announcements.add(announcement);
    }

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



    public String getDiscription() {
        return description;
    }

    public void setDiscription(String discription) {
        this.description = discription;
    }

    public void addStudent(Student student){
        this.student.add(student);
        studentNum++;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void decStudentNum() {
        this.studentNum--;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public Boolean subjectIsFull(){
        if(this.studentNum==this.no_student){
            return true;
        }else
            return false;
    }
    public boolean equals(Subject obj) {
        if(this.getName().equals(obj.getName())&&this.getDay().equals(obj.getDay())&&this.getTime().equals(obj.getTime())){
            return true;
        }
        return false;
    }
}
