package facade;

import entity.Semester;
import entity.Student;
import entity.Teacher;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import mappers.StudentInfo;

public class Facade {

    EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Student> getAllStudents() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<Student>) em.createNamedQuery("Student.findAll").getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> getAllStudentsByFirstName(String firstName) {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<Student>) em.createNamedQuery("Student.findByFirstname").setParameter("firstname", firstName).getResultList();
        } finally {
            em.close();
        }
    }

    public void createStudent(Student s) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Semester> getAllSemesters() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<Semester>) em.createNamedQuery("Semester.findAll").getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> getAllStudentsByLastName(String lastName) {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<Student>) em.createNamedQuery("Student.findByLastname").setParameter("lastname", lastName).getResultList();
        } finally {
            em.close();
        }
    }

    public long getAllStudentBySemesterName(String semesterName) {
        EntityManager em = emf.createEntityManager();
        try {
            return (long) em.createNamedQuery("Student.countBySemestername").setParameter("semestername", semesterName).getSingleResult();
        } finally {
            em.close();
        }
    }

    public long getAllStudentFromAllSemesters() {
        EntityManager em = emf.createEntityManager();
        try {
            return (long) em.createNamedQuery("Student.countByAllSemesterid").getSingleResult(); // .setParameter("minimumID", 0)
        } finally {
            em.close();
        }
    }

    public Teacher getTeacherWithMostSemesters() {
        EntityManager em = emf.createEntityManager();
        try {
            return (Teacher) em.createQuery("SELECT t FROM Teacher t LEFT JOIN t.semesterCollection as s GROUP BY t.id ORDER BY t DESC").getResultList().get(0);
        } finally { // SELECT NEW StudentInfo(t.firstName, t.lastName)
            em.close();
        }
    }

    public List<StudentInfo> getAllStudentsInfo() {
        EntityManager em = emf.createEntityManager();
        List<Student> students;
        try {
            students = (List<Student>) em.createNamedQuery("Student.findAll").getResultList();
        } finally {
            em.close();
        }
        List<StudentInfo> infos = new ArrayList();
        for (int i = 0; i < students.size(); i++) {
            infos.add(new StudentInfo(students.get(i).getFirstname(), students.get(i).getLastname()));
        }
        return infos;
    }

    public StudentInfo getStudentInfoById(long id) {
        EntityManager em = emf.createEntityManager();
        Student student;
        try {
            student = (Student) em.createNamedQuery("Student.findById").setParameter("id", id).getSingleResult();
        } finally {
            em.close();
        }
        return new StudentInfo(student.getFirstname(), student.getLastname());
    }

}
