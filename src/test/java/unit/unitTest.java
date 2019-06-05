package unit;

import entity.Semester;
import entity.Student;
import entity.Teacher;
import facade.Facade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import mappers.StudentInfo;
import org.junit.Assert;
import org.junit.Test;

public class unitTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu", null);

    Facade facade = new Facade(emf);

    @Test
    public void getAllStudents() {
        List<Student> students = facade.getAllStudents();
        Assert.assertTrue(6 <= students.size());
    }

    @Test
    public void getAllStudentsByFirstName() {
        List<Student> students = facade.getAllStudentsByFirstName("Anders");
        Assert.assertEquals(1, students.size());
    }

    @Test
    public void createStudent() {
        Student s = new Student();
        s.setFirstname("Igor");
        s.setLastname("Blovardor");
        facade.createStudent(s);
        Student student = facade.getAllStudentsByFirstName("Igor").get(0);
        Assert.assertEquals(s.getLastname(), student.getLastname());

    }

    @Test
    public void assignSemesterToStudent() {
        Student s = new Student();
        Semester sem = facade.getAllSemesters().get(0);

        s.setFirstname("Ivan");
        s.setLastname("Smirskahan");
        s.setSemester(sem);

        //sem.getStudentCollection().add(s);

        facade.createStudent(s);
        Student student = facade.getAllStudentsByFirstName("Ivan").get(0);
        Assert.assertEquals(s.getSemester().getName(), student.getSemester().getName());
    }

    @Test
    public void getAllStudentByLastName() {
        List<Student> students = facade.getAllStudentsByLastName("And");
        Assert.assertEquals(2, students.size());
    }

    @Test
    public void getAllStudentBySemesterName() {
        long count = facade.getAllStudentBySemesterName("CLdat-b14e");
        Assert.assertEquals(2, count);
    }

    @Test
    public void getAllStudentFromAllSemesters() {
        long count = facade.getAllStudentFromAllSemesters();
        Assert.assertTrue(6 <= count);
    }

    @Test
    public void getTeacherWithMostSemesters() {
        Teacher teacher = facade.getTeacherWithMostSemesters();
        Assert.assertEquals(3, (long) teacher.getId());
    }

    @Test
    public void getStudentsInfo() {
        List<StudentInfo> students = facade.getAllStudentsInfo();
        Assert.assertTrue(6 <= students.size());
    }

    @Test
    public void getStudentInfoById() {
        StudentInfo studentInfo = facade.getStudentInfoById(1);
        Assert.assertEquals("Jens Jensen", studentInfo.fullName);
    }
}
