package pkg17.ScoreManagement.model;

import javax.xml.crypto.Data;
import java.sql.Date;

public class CourseInfo {
    private String courseNo;
    private String courseName;
    private char status;
    private String semster;

    public CourseInfo() {
    }

    public CourseInfo(String courseNo, String courseName, char status, String semster) {
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.status = status;
        this.semster = semster;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getSemster() {
        return semster;
    }

    public void setSemster(String semster) {
        this.semster = semster;
    }

    @Override
    public String toString() {
        return  this.courseName;
    }
}