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
        Concessions instance = new Concessions();
        assertEquals(instance.getUid(), null);
        assertEquals(instance.getStudentNo(), null);
        assertEquals(instance.getPdfName(), null);
        assertEquals(instance.getCourseCode(), null);
        assertEquals(instance.getPdfUrl(), null);
        assertEquals(instance.getStatus(), null);
    }

    @Test
    public void testFullConstructor() {
        Concessions instance = new Concessions("testUID", "12345", "test.pdf", "COMS1001", "url/test.pdf", "pending");
        assertEquals("testUID", instance.uid);
        assertEquals("12345", instance.studentNo);
        assertEquals("test.pdf", instance.pdfName);
        assertEquals("COMS1001", instance.courseCode);
        assertEquals("url/test.pdf", instance.getPdfUrl());
        assertEquals("pending", instance.getStatus());

    }

    @Test
    public void testSetStatus() {
        String status = "YqAJgqrNL3c4cWJ2p2S8hUIl9K22";
        Concessions instance = new Concessions();
        instance.setStatus(status);
        assertEquals(instance.getStatus(), status);
    }

    @Test
    public void testSetUid() {
        String uid = "YqAJgqrNL3c4cWJ2p2S8hUIl9K22";
        Concessions instance = new Concessions();
        instance.setUid(uid);
        assertEquals(instance.getUid(), uid);
    }

    @Test
    public void testSetPdfName() {
        String pdfName = "11434236_M12__2018-09-07";
        Concessions instance = new Concessions();
        instance.setPdfName(pdfName);
        assertEquals(instance.getPdfName(), pdfName);
    }

    @Test
    public void testSetCourseCode() {
        String courseCode = "MATH3001";
        Concessions instance = new Concessions();
        instance.setCourseCode(courseCode);
        assertEquals(instance.getCourseCode(), courseCode);
    }

    @Test
    public void testSetPdfUrl() {
        String pdfUrl = "https://firebasestorage.googleapis.com/v0/b/mid-year-registration-ef4af.appspot.com/o?name=Concessions%2F1153631_MATH3034__2018-09-06.pdf&uploadType=resumable&upload_id=AEnB2UqGfahaQ3m1DE21ov3aADC-IlbiyMZF4l2CuxBe2FWR9Y2e_SXeqJsXQH6vctAwKK4iVJdZgUBuef1VrZs9M2AJcWwqKxS4fRUPjMkgN06R-6_R2qg&upload_protocol=resumable";
        Concessions instance = new Concessions();
        instance.setPdfUrl(pdfUrl);
        assertEquals(instance.getPdfUrl(), pdfUrl);
    }

    @Test
    public void testSetStudentNo() {
        String stdNo = "1153631";
        Concessions instance = new Concessions();
        instance.setStudentNo(stdNo);
        assertEquals(instance.getStudentNo(), stdNo);
    }

}

