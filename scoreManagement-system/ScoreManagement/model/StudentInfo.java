package pkg17.ScoreManagement.model;

public class StudentInfo {
    private String studentId;
    private String name;
    private int age;
    private char gender;
    private String password;
    private char status;
    private String clazzNo;


    public StudentInfo() {
    }

    public StudentInfo(String studentId, String name, int age, char gender, String password, char status, String clazzNo) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.password = password;
        this.status = status;
        this.clazzNo = clazzNo;
    }

    public StudentInfo(String studentId, String name, int age, char gender,String clazzNo) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.clazzNo = clazzNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getClazzNo() {
        return clazzNo;
    }

    public void setClazzNo(String clazzNo) {
        this.clazzNo = clazzNo;
    }
}
