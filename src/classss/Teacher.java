package classss;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Teacher extends Account {
    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<Subject>();

    private String post;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id_teacher;

    public long getId_teacher() {
        return id_teacher;
    }

    public Teacher(String password, String name, String surname, String birthday, String email, String phonenumber, String post){
        super(password,name,surname,birthday,email,phonenumber);
        this.post = post;
    }

    public void addSubjects(Subject subject){
        subject.setTeacher(this);
        subjects.add(subject);
    }



    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
