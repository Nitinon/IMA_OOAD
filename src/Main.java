import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.prefs.Preferences;
import classss.Subject;


public class Main extends Application {
    public static void main (String[] args){

        launch(args);
    }
    @Override
    public void start (Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("front/login.fxml"));
        primaryStage.setTitle("IMA");
        primaryStage.setScene(new Scene(root,1280,720));
        primaryStage.show();
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("aa","eieieieiei");
    }

    public static void createSubject(long id,String name,int no_student,String time,int section){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        classss.Subject a = new classss.Subject(id,name,no_student,time,section);
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    public static void createStudent(String password, String name, String surname, String birthday, String email, String phonenumber, int year_of_study,String faculty) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        classss.Student a = new classss.Student(password,name,surname,birthday,email,phonenumber,year_of_study, faculty);

        em.persist(a);
        em.getTransaction().commit();
        em.getTransaction().begin();
        a.setId(a.getId_student()+200000);
        em.getTransaction().commit();
        em.close();
        emf.close();

    }
    public static void createTeacher(String password, String name, String surname, String birthday, String email, String phonenumber, String post) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        classss.Teacher a = new classss.Teacher(password,name,surname,birthday,email,phonenumber,post);

        em.persist(a);
        em.getTransaction().commit();
        em.getTransaction().begin();
        a.setId(a.getId_teacher()+100000);
        em.getTransaction().commit();
        em.close();
        emf.close();

    }
    public static void enroolCourse(int id_stu,int id_sub){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<classss.Student> results1 = query1.getResultList();

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        em.getTransaction().begin();
        results1.get(0).addSubject(results2.get(0));
        //em.persist(results1.get(0));
        //em.persist(results2.get(0));
        em.getTransaction().commit();
        em.close();
        emf.close();
    }


    public static void deleteCourse(int id_stu,int id_sub){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<classss.Student> results1 = query1.getResultList();

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        em.getTransaction().begin();

        for(classss.Subject a:results1.get(0).getSubject()){
            if(a.getId_sub()==id_sub){
                results1.get(0).getSubject().remove(a);
                break;
            }

        }
        for(classss.Student b:results2.get(0).getStudent()){
            if(b.getId()==id_stu){
                results2.get(0).getStudent().remove(b);
                break;
            }

        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void openCourse(int id_tea,int id_sub){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Teacher c Where c.id =" + id_tea + "";
        TypedQuery<classss.Teacher> query1 = em.createQuery(sql1, classss.Teacher.class);
        List<classss.Teacher> results1 = query1.getResultList();

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        em.getTransaction().begin();
        results1.get(0).addSubjects(results2.get(0));
        //em.persist(results1.get(0));
        //em.persist(results2.get(0));
        // System.out.println(results1);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    public static void delCourse(int id_tea,int id_sub){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

       /* String sql1 = "SELECT c FROM Teacher c Where c.id =" + id_tea + "";
        TypedQuery<Student> query1 = em.createQuery(sql1,Student.class);
        List<Student> results1 = query1.getResultList(); */

        String sql2 = "SELECT c FROM Subject c Where c.id_sub =" + id_sub + "";
        TypedQuery<classss.Subject> query2 = em.createQuery(sql2, classss.Subject.class);
        List<classss.Subject> results2 = query2.getResultList();

        for(classss.Student a:results2.get(0).getStudent()){
            for(classss.Subject b:a.getSubject()){
                if(b.getId_sub()==id_sub){
                    a.getSubject().remove(b);
                    break;
                }
            }
        }

        em.getTransaction().begin();
        em.remove(results2.get(0));
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static void createScore(int idStudent, int idSubject, String topic, int point) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        classss.Score a = new classss.Score(idStudent,idSubject,topic,point);
        em.persist(a);
        em.getTransaction().commit();
        em.close();
        emf.close();

    }

    public static int viewScore(int idStudent, int idSubject, String topic){
        int score = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();

        String sql1 = "SELECT c FROM Score c Where c.IdStudent = " + idStudent + " AND c.IdSubject = "+idSubject ;
        TypedQuery<classss.Score> query1 = em.createQuery(sql1, classss.Score.class);
        List<classss.Score> results = query1.getResultList();
        em.getTransaction().begin();
        for(classss.Score c : results){
            if(c.getTopic().equals(topic)){
                score = c.getPoint();
            }
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        return score;
    }

    public static void editScore(int idStudent, int idSubject, String topic,int point){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Score c Where c.IdStudent = " + idStudent + " AND c.IdSubject = "+idSubject;
        TypedQuery<classss.Score> query1 = em.createQuery(sql1, classss.Score.class);
        List<classss.Score> results = query1.getResultList();
        em.getTransaction().begin();
        for(classss.Score c : results){
            if(c.getTopic().equals(topic)){
                c.setPoint(point);
            }
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public static classss.Student getObjStudent(int id_stu){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Student c Where c.id =" + id_stu + "";
        TypedQuery<classss.Student> query1 = em.createQuery(sql1, classss.Student.class);
        List<classss.Student> results1 = query1.getResultList();
        if(results1.size()==0){
            return null;
        }
        else{
            return results1.get(0);
        }
    }

    public static classss.Teacher getObjTeacher(int id_tea){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/AccountDB.odb");
        EntityManager em = emf.createEntityManager();
        String sql1 = "SELECT c FROM Teacher c Where c.id =" + id_tea + "";
        TypedQuery<classss.Teacher> query1 = em.createQuery(sql1, classss.Teacher.class);
        List<classss.Teacher> results1 = query1.getResultList();
        if(results1.size()==0){
            return null;
        }
        else{
            return results1.get(0);
        }
    }

}
