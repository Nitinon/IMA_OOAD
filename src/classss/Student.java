package classss;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

@Entity
public class Student extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_student;
    private int year_of_study;
    private String faculty;

    public long getId_student() {
        return id_student;
    }

    //private static final long serialVersionUID = 1L;
    private List<Subject> subject = new ArrayList<Subject>();

    public Student(String password, String name, String surname, String birthday, String email, String phonenumber, int year_of_study, String faculty) {
        super(password, name, surname, birthday, email, phonenumber);
        this.year_of_study = year_of_study;
        this.faculty = faculty;
    }


    public List<Subject> getSubject() {
        return subject;
    }

    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }


    public void addSubject(Subject subject) {
        subject.addStudent(this);
        this.subject.add(subject);
    }

    public int getYear_of_study() {
        return year_of_study;
    }

    public void setYear_of_study(int year_of_study) {
        this.year_of_study = year_of_study;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}

