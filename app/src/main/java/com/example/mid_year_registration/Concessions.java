package com.example.mid_year_registration;

public class Concessions {
    String uid;
    String studentNo;
    String pdfName;
    String courseCode;
    String pdfUrl;
    String status;

    public Concessions(String uid, String studentNo, String pdfName, String courseCode, String pdfUrl, String status) {
        this.uid = uid;
        this.studentNo = studentNo;
        this.pdfName = pdfName;
        this.courseCode = courseCode;
        this.pdfUrl = pdfUrl;
        this.status = status;
    }

    public Concessions(){

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

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }





}
