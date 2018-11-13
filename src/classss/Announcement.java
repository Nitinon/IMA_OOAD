package classss;

import javax.persistence.*;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) private long id;

    String type;
    String info;
    String title;
    String date;

    public long getId() {
        return id;
    }

    @ManyToOne private Subject subjectsss;

    public Announcement(String type, String info, String title, String date) {
        this.type = type;
        this.info = info;
        this.title = title;
        this.date = date;
    }

    public Subject getSubjectsss() {
        return subjectsss;
    }



    public void setSubjectsss(Subject subjectsss) {
        this.subjectsss = subjectsss;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", subjectsss=" + subjectsss +
                '}';
    }
}
