package com.example.mid_year_registration;

public class CoordinatorResponse {
    String uidStd;
    String uidCoord;
    String pdfId;
    String comment;
    String status;

    public CoordinatorResponse(){

    }

    public CoordinatorResponse(String uidStd, String uidCoord, String pdfId, String comment, String status) {
        this.uidStd = uidStd;
        this.uidCoord = uidCoord;
        this.pdfId = pdfId;
        this.comment = comment;
        this.status = status;
    }


    public String getUidStd() {
        return uidStd;
    }

    public void setUidStd(String uidStd) {
        this.uidStd = uidStd;
    }

    public String getUidCoord() {
        return uidCoord;
    }

    public void setUidCoord(String uidCoord) {
        this.uidCoord = uidCoord;
    }

    public String getPdfId() {
        return pdfId;
    }

    public void setPdfId(String pdfId) {
        this.pdfId = pdfId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }


}
