package pkg17.ScoreManagement.model;


public class ScoreInfo {
    private String name;
    private String studentId;
    private String courseNo;
    private String score;
    private String courseName;

    public ScoreInfo() {
    }

    public ScoreInfo(String name, String studentId, String courseNo, String score, String courseName) {
        this.name = name;
        this.studentId = studentId;
        this.courseNo = courseNo;
        this.score = score;
        this.courseName = courseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
