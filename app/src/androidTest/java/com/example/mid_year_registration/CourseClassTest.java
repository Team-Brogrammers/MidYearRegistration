package com.example.mid_year_registration;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CourseClassTest {

    @Test
    public void testDefaultConstructor(){
        Course instance = new Course();
        assertEquals("", instance.getCode());
        assertEquals("", instance.getCo_requisite());
        assertEquals("0", instance.getCredits());
        assertEquals("", instance.getDescription());
        assertEquals("0", instance.getLevel());
        assertEquals("", instance.getPre_requisite());
        assertEquals("1", instance.getSemester());
        assertEquals("", instance.getCoordinator_uid());
    }

    @Test
    public void testFullConstructor(){
        Course instance = new Course("COMS1001", "Computer Science Test Course");
        assertEquals("COMS1001", instance.getCode());
        assertEquals("", instance.getCo_requisite());
        assertEquals("0", instance.getCredits());
        assertEquals("Computer Science Test Course", instance.getDescription());
        assertEquals("0", instance.getLevel());
        assertEquals("", instance.getPre_requisite());
        assertEquals("1", instance.getSemester());
        assertEquals("", instance.getCoordinator_uid());
    }

    @Test
    public void testSetCode(){
        Course instance = new Course();
        instance.setCode("COMS1001");
        assertEquals("COMS1001", instance.getCode());
    }

    @Test
    public void testSetCo_requisite(){
        Course instance = new Course();
        instance.setCo_requisite("COMS1002");
        assertEquals("COMS1002", instance.getCo_requisite());
    }

    @Test
    public void testSetCredits(){
        Course instance = new Course();
        instance.setCredits("8");
        assertEquals("8", instance.getCredits());
    }

    @Test
    public void testSetDescription(){
        Course instance = new Course();
        instance.setDescription("Test Course");
        assertEquals("Test Course", instance.getDescription());
    }

    @Test
    public void testSetLevel(){
        Course instance = new Course();
        instance.setLevel("1");
        assertEquals("1", instance.getLevel());
    }

    @Test
    public void testSetPre_requisite(){
        Course instance = new Course();
        instance.setPre_requisite("COMS1001");
        assertEquals("COMS1001", instance.getPre_requisite());
    }

    @Test
    public void testSetSemester(){
        Course instance = new Course();
        instance.setSemester("1");
        assertEquals("1", instance.getSemester());
    }

    @Test
    public void testSetCoordinator_uid(){
        Course instance = new Course();
        instance.setCoordinator_uid("xkcd");
        assertEquals("xkcd", instance.getCoordinator_uid());
    }
}
