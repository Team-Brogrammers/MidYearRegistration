package com.example.mid_year_registration;

import android.support.test.filters.LargeTest;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CoordinatorConcessionTest{

    // Test the empty constructor
    @Test
    public void testVoidConcession() {
        CoordinatorConcession instance = new CoordinatorConcession();
        assertEquals(instance.getUid(), "");
        assertEquals(instance.getStudentNo(), "");
        assertEquals(instance.getPdfName(), "");
        assertEquals(instance.getCourseCode(), "");
        assertEquals(instance.getPdfUrl(), "");
        assertEquals(instance.getPdfUrl(), "");
        assertEquals(instance.getComment(), "");
        assertEquals(instance.getStatus(), "Test for status");
    }

    // Test the full constructor
    @Test
    public void testFullConcession(){
        CoordinatorConcession instance = new CoordinatorConcession("TestUid", "test567", "TestPdfName", "TEST1001", "Test Comment", "Test_URL.test.com", "Test for status");
        assertEquals(instance.getUid(), "TestUid");
        assertEquals(instance.getStudentNo(), "test567");
        assertEquals(instance.getPdfName(), "TestPdfName");
        assertEquals(instance.getCourseCode(),"TEST1001");
        assertEquals(instance.getComment(), "Test Comment");
        assertEquals(instance.getPdfUrl(), "Test_URL.test.com");
        assertEquals(instance.getStatus(), "Test for status");
    }

    @Test
    public void testSetStatus() {
        String status = "Please come see me";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setStatus(status);
        assertEquals(instance.getStatus(), status);
    }

    @Test
    public void testGetStatus() {
        String expStatus = "Please come see me";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setStatus("Please come see me");
        String status = instance.getStatus();
        assertEquals(expStatus, status);
    }


    @Test
    public void testSetComment() {
        String comment = "Please come see me";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setComment(comment);
        assertEquals(instance.getComment(), comment);
    }

    @Test
    public void testGetComment() {
        String expComment = "Please come see me";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setComment("Please come see me");
        String comment = instance.getComment();
        assertEquals(expComment, comment);
    }

    @Test
    public void testSetUid() {
        String uid = "YqAJgqrNL3c4cWJ2p2S8hUIl9K22";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setUid(uid);
        assertEquals(instance.getUid(), uid);
    }

    @Test
    public void testGetUid() {
        String expUid = "YqAJgqrNL3c4cWJ2p2S8hUIl9K22";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setUid("YqAJgqrNL3c4cWJ2p2S8hUIl9K22");
        String uid = instance.getUid();
        assertEquals(expUid, uid);
    }

    @Test
    public void testSetPdfName() {
        String pdfName = "11434236_M12__2018-09-07";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setPdfName(pdfName);
        assertEquals(instance.getPdfName(), pdfName);
    }

    @Test
    public void testGetPdfName() {
        String expPdfName = "11434236_M12__2018-09-07";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setPdfName("11434236_M12__2018-09-07");
        String pdfName = instance.getPdfName();
        assertEquals(expPdfName, pdfName);
    }

    @Test
    public void testSetCourseCode() {
        String courseCode = "MATH3001";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setCourseCode(courseCode);
        assertEquals(instance.getCourseCode(), courseCode);
    }

    @Test
    public void testGetCourseCode() {
        String expCourseCode = "MATH3001";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setCourseCode("MATH3001");
        String courseCode = instance.getCourseCode();
        assertEquals(expCourseCode, courseCode);
    }

    @Test
    public void testSetPdfUrl() {
        String pdfUrl = "https://firebasestorage.googleapis.com/v0/b/mid-year-registration-ef4af.appspot.com/o?name=CoordinatorConcession%2F1153631_MATH3034__2018-09-06.pdf&uploadType=resumable&upload_id=AEnB2UqGfahaQ3m1DE21ov3aADC-IlbiyMZF4l2CuxBe2FWR9Y2e_SXeqJsXQH6vctAwKK4iVJdZgUBuef1VrZs9M2AJcWwqKxS4fRUPjMkgN06R-6_R2qg&upload_protocol=resumable";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setPdfUrl(pdfUrl);
        assertEquals(instance.getPdfUrl(), pdfUrl);
    }

    @Test
    public void testGetPdfUrl() {
        String expPdfUrl = "https://firebasestorage.googleapis.com/v0/b/mid-year-registration-ef4af.appspot.com/o?name=CoordinatorConcession%2F11434236_M12__2018-09-07.pdf&uploadType=resumable&upload_id=AEnB2Upgf0l364_j740hccLiHo-JApF5dH6E33vzNkKIblxewE3BQQY6tcfYuB0ZVJUy1kFiCD1cLjJSKQOfhMC76EykaUcKX3wZ9GLfLg2NOckRaYep6d0&upload_protocol=resumable";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setPdfUrl("https://firebasestorage.googleapis.com/v0/b/mid-year-registration-ef4af.appspot.com/o?name=CoordinatorConcession%2F11434236_M12__2018-09-07.pdf&uploadType=resumable&upload_id=AEnB2Upgf0l364_j740hccLiHo-JApF5dH6E33vzNkKIblxewE3BQQY6tcfYuB0ZVJUy1kFiCD1cLjJSKQOfhMC76EykaUcKX3wZ9GLfLg2NOckRaYep6d0&upload_protocol=resumable");
        String pdfUrl = instance.getPdfUrl();
        assertEquals(expPdfUrl, pdfUrl);
    }



    @Test
    public void testSetStudentNo() {
        String stdNo = "1153631";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setStudentNo(stdNo);
        assertEquals(instance.getStudentNo(), stdNo);
    }

    @Test
    public void testGetStudentNo() {
        String expStdNo = "1153631";
        CoordinatorConcession instance = new CoordinatorConcession();
        instance.setStudentNo("1153631");
        String stdNo = instance.getStudentNo();
        assertEquals(expStdNo, stdNo);
    }
}

