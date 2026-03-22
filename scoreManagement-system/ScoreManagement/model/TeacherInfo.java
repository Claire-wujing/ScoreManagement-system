package pkg17.ScoreManagement.model;

public class TeacherInfo {
    private String teacherId;
    private String name;
    private int age;
    private char gender;
    private String password;
    private char status;
    private String courseNo;

    public TeacherInfo() {}

    public TeacherInfo(String teacherId, String name, int age, char gender, String password, char status, String courseNo) {
        this.teacherId = teacherId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.password = password;
        this.status = status;
        this.courseNo = courseNo;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
