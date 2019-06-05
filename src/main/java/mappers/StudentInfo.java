package mappers;

public class StudentInfo {

    public String fullName;
    public long studentId;
    public String classNameThisSemester;
    public String classDescription;

    public StudentInfo(String firstName, String lastName) {
        this.fullName = firstName + " " + lastName;
    }

}
