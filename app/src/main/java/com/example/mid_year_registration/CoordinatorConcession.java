package com.example.mid_year_registration;

public class CoordinatorConcession {

    String uid;
    String studentNo;
    String pdfName;
    String courseCode;
    String comment;
    String pdfUrl;

    // Overload empty constructor to keep firebase happy when downloading concessions created under the Concessions.class scheme.
    // The attributes will be set by the firebase fetch methods using the get() and set() methods in this class.
    // If the concession has not been commented, the comment will default to an empty string.
    public CoordinatorConcession(){
        this.uid = "";
        this.studentNo = "";
        this.pdfName = "";
        this.courseCode = "";
        this.comment = "";
        this.pdfUrl = "";
    }

    public CoordinatorConcession(String uid, String studentNo, String pdfName, String courseCode, String comment, String pdfUrl) {
        this.uid = uid;
        this.studentNo = studentNo;
        this.pdfName = pdfName;
        this.courseCode = courseCode;
        this.comment = comment;
        this.pdfUrl = pdfUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }



}
