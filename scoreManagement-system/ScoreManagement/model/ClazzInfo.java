package pkg17.ScoreManagement.model;

public class ClazzInfo {
    private String clazzNo;
    private String status;
    private String studentCount; // 学生人数

    public ClazzInfo() {
    }

    public ClazzInfo(String clazzNo, String status) {
        this.clazzNo = clazzNo;
        this.status = status;
    }

    public String getClazzNo() {
        return clazzNo;
    }

    public void setClazzNo(String clazzNo) {
        this.clazzNo = clazzNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }
}
