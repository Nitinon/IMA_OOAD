package classss;
import java.io.Serializable;
import javax.persistence.*;

@Entity

public class Account implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    private String password;
    private String name;
    private String surname;
    private String birthday;
    private String email;
    private String phonenumber;

    public Account(){
    }
    Account(String password, String name, String surname, String birthday, String email, String phonenumber){
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
