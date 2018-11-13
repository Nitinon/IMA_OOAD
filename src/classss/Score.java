package classss;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.Serializable;
import java.util.List;

@Entity
public class Score implements Serializable{
    private int IdStudent;
    private int IdSubject;
    private String Topic;
    private int point;
    private int max;

    public Score(int idStudent, int idSubject, String topic, int point,int max) {
        IdStudent = idStudent;
        IdSubject = idSubject;
        Topic = topic;
        this.point = point;
        this.max=max;
    }

    public int getIdStudent() {
        return IdStudent;
    }

    public void setIdStudent(int idStudent) {
        IdStudent = idStudent;
    }

    public int getIdSubject() {
        return IdSubject;
    }

    public void setIdSubject(int idSubject) {
        IdSubject = idSubject;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "Score{" +
                "IdStudent='" + IdStudent + '\'' +
                ", IdSubject='" + IdSubject + '\'' +
                ", Topic='" + Topic + '\'' +
                '}';
    }
}
