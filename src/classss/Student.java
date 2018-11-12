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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id_student;

    public long getId_student() {
        return id_student;
    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //private static final long serialVersionUID = 1L;
      private List<Subject> subject = new ArrayList<Subject>();

    public List<Subject> getSubject() {
        return subject;
    }

    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }

    private int year_of_study;


    public Student(String password, String name, String surname, String birthday, String email, String phonenumber, int year_of_study){
        super(password,name,surname,birthday,email,phonenumber);
        this.year_of_study = year_of_study;
    }

    public void addSubject(Subject subject){
        subject.addStudent(this);
        this.subject.add(subject);
    }

    public int getYear_of_study() {
        return year_of_study;
    }

    public void setYear_of_study(int year_of_study) {
        this.year_of_study = year_of_study;
    }





}
