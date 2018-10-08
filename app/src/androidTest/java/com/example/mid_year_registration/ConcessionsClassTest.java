package com.example.mid_year_registration;

import android.support.test.filters.LargeTest;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ConcessionsClassTest{
    @Test
    public void testConcession() {
        Concessions instance = new Concessions(null, null, null, null, null);
        assertEquals(instance.getUid(), null);
        assertEquals(instance.getStudentNo(), null);
        assertEquals(instance.getPdfName(), null);
        assertEquals(instance.getCourseCode(), null);
        assertEquals(instance.getPdfUrl(), null);
    }

    @Test
    public void testSetUid() {
        String uid = "YqAJgqrNL3c4cWJ2p2S8hUIl9K22";
        Concessions instance = new Concessions();
        instance.setUid(uid);
        assertEquals(instance.getUid(), uid);
    }

    @Test
    public void testGetUid() {
        String expUid = "YqAJgqrNL3c4cWJ2p2S8hUIl9K22";
        Concessions instance = new Concessions();
        instance.setUid("YqAJgqrNL3c4cWJ2p2S8hUIl9K22");
        String uid = instance.getUid();
        assertEquals(expUid, uid);
    }

    @Test
    public void testSetPdfName() {
        String pdfName = "11434236_M12__2018-09-07";
        Concessions instance = new Concessions();
        instance.setPdfName(pdfName);
        assertEquals(instance.getPdfName(), pdfName);
    }

    @Test
    public void testGetPdfName() {
        String expPdfName = "11434236_M12__2018-09-07";
        Concessions instance = new Concessions();
        instance.setPdfName("11434236_M12__2018-09-07");
        String pdfName = instance.getPdfName();
        assertEquals(expPdfName, pdfName);
    }

    @Test
    public void testSetCourseCode() {
        String courseCode = "MATH3001";
        Concessions instance = new Concessions();
        instance.setCourseCode(courseCode);
        assertEquals(instance.getCourseCode(), courseCode);
    }

    @Test
    public void testGetCourseCode() {
        String expCourseCode = "MATH3001";
        Concessions instance = new Concessions();
        instance.setCourseCode("MATH3001");
        String courseCode = instance.getCourseCode();
        assertEquals(expCourseCode, courseCode);
    }

    @Test
    public void testSetPdfUrl() {
        String pdfUrl = "https://firebasestorage.googleapis.com/v0/b/mid-year-registration-ef4af.appspot.com/o?name=Concessions%2F1153631_MATH3034__2018-09-06.pdf&uploadType=resumable&upload_id=AEnB2UqGfahaQ3m1DE21ov3aADC-IlbiyMZF4l2CuxBe2FWR9Y2e_SXeqJsXQH6vctAwKK4iVJdZgUBuef1VrZs9M2AJcWwqKxS4fRUPjMkgN06R-6_R2qg&upload_protocol=resumable";
        Concessions instance = new Concessions();
        instance.setPdfUrl(pdfUrl);
        assertEquals(instance.getPdfUrl(), pdfUrl);
    }

    @Test
    public void testGetPdfUrl() {
        String expPdfUrl = "https://firebasestorage.googleapis.com/v0/b/mid-year-registration-ef4af.appspot.com/o?name=Concessions%2F11434236_M12__2018-09-07.pdf&uploadType=resumable&upload_id=AEnB2Upgf0l364_j740hccLiHo-JApF5dH6E33vzNkKIblxewE3BQQY6tcfYuB0ZVJUy1kFiCD1cLjJSKQOfhMC76EykaUcKX3wZ9GLfLg2NOckRaYep6d0&upload_protocol=resumable";
        Concessions instance = new Concessions();
        instance.setPdfUrl("https://firebasestorage.googleapis.com/v0/b/mid-year-registration-ef4af.appspot.com/o?name=Concessions%2F11434236_M12__2018-09-07.pdf&uploadType=resumable&upload_id=AEnB2Upgf0l364_j740hccLiHo-JApF5dH6E33vzNkKIblxewE3BQQY6tcfYuB0ZVJUy1kFiCD1cLjJSKQOfhMC76EykaUcKX3wZ9GLfLg2NOckRaYep6d0&upload_protocol=resumable");
        String pdfUrl = instance.getPdfUrl();
        assertEquals(expPdfUrl, pdfUrl);
    }



    @Test
    public void testSetStudentNo() {
        String stdNo = "1153631";
        Concessions instance = new Concessions();
        instance.setStudentNo(stdNo);
        assertEquals(instance.getStudentNo(), stdNo);
    }

    @Test
    public void testGetStudentNo() {
        String expStdNo = "1153631";
        Concessions instance = new Concessions();
        instance.setStudentNo("1153631");
        String stdNo = instance.getStudentNo();
        assertEquals(expStdNo, stdNo);
    }
}

