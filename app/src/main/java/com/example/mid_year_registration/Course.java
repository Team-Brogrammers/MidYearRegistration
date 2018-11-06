package com.example.mid_year_registration;

import java.util.ArrayList;

public class Course {

    private String Code;
    private String Co_requisite;
    private String Credits;
    private String Description;
    private String Level;
    private String Pre_requisite;
    private String School;
    private String Semester;
    private String Coordinator_uid;


    public Course(){
        this.Code = "";
        this.Co_requisite = "";
        this.Credits = "0";
        this.Description = "";
        this.Level = "0";
        this.Pre_requisite = "";
        this.Semester = "1";
        this.Coordinator_uid = "";
    }

    public Course(String Code, String Description){
        this.Code = Code;
        this.Co_requisite = "";
        this.Credits = "0";
        this.Description = Description;
        this.Level = "0";
        this.Pre_requisite = "";
        this.Semester = "1";
        this.Coordinator_uid = "";
    }


    public String getCode() {
        return Code;
    }

    public String getCo_requisite() {
        return Co_requisite;
    }

    public String getCredits() {
        return Credits;
    }

    public String getDescription() {
        return Description;
    }

    public String getLevel() {
        return Level;
    }

    public String getPre_requisite() {
        return Pre_requisite;
    }

    public String getSchool() {
        return School;
    }

    public String getSemester() {
        return Semester;
    }

    public String getCoordinator_uid() {
        return Coordinator_uid;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setCo_requisite(String co_requisite) {
        Co_requisite = co_requisite;
    }

    public void setCredits(String credits) {
        Credits = credits;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public void setPre_requisite(String pre_requisite) {
        Pre_requisite = pre_requisite;
    }

    public void setSchool(String school) {
        School = school;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public void setCoordinator_uid(String coordinator_uid) {
        Coordinator_uid = coordinator_uid;
    }
}
