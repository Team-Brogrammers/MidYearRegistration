package com.example.mid_year_registration;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

public class PasswordResetUnitTest extends ActivityInstrumentationTestCase2<PasswordResetActivity> {
    PasswordResetActivity activity;

    public PasswordResetUnitTest() {
        super(PasswordResetActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void test(){
        assertEquals(activity.isvalideEmail(), true);
        //assertEquals(activity.checkEmailExistsInFirebase("123456@wits.ac.za"),);
    }

}
