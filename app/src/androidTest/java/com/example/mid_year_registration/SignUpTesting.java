package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class SignUpTesting extends ActivityInstrumentationTestCase2<SignUpActivity> {
    SignUpActivity activity;

    public SignUpTesting() {
        super(SignUpActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testImage(){
        onView(withId(R.id.imageView3)).check(matches(isDisplayed()));
    }

    @SmallTest
    public void testInValidPassword(){
        onView(withId(R.id.usernameEditText)).perform(typeText("tes@student.wits.ac.za"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("ab"), closeSoftKeyboard());
        onView(withId(R.id.submitButton)).perform(click());
    }

    @SmallTest
    public void testValidStudent(){
        onView(withId(R.id.usernameEditText)).perform(typeText("test3@student.wits.ac.za"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("testing3"), closeSoftKeyboard());
        onView(withId(R.id.submitButton)).perform(click());
    }

    @SmallTest
    public void testValidCoordinator(){
        onView(withId(R.id.usernameEditText)).perform(typeText("testing3@wits.ac.za"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("testing3"), closeSoftKeyboard());
        onView(withId(R.id.adminCheckBox)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.submitButton)).perform(click());
    }

//    @SmallTest
//    public void testNoPassword(){
//
//        onView(withId(R.id.usernameEditText)).perform(typeText("invalidsocialate@gmail.com"), closeSoftKeyboard());
//        onView(withId(R.id.passwordEditText)).perform(typeText("   "), closeSoftKeyboard());
//        onView(withId(R.id.submitButton)).perform(click());
//    }
//
//    @SmallTest
//    public void testNoEmail(){
//        onView(withId(R.id.usernameEditText)).perform(typeText("   "), closeSoftKeyboard());
//        onView(withId(R.id.passwordEditText)).perform(typeText("furry123"), closeSoftKeyboard());
//        onView(withId(R.id.submitButton)).perform(click());
//    }

    @SmallTest
    public void testInvalidEmail(){
        onView(withId(R.id.usernameEditText)).perform(typeText("invalidsocialate"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("furry123"), closeSoftKeyboard());
        onView(withId(R.id.submitButton)).perform(click());
    }

//    @SmallTest
//    public void loginCoord(){
//        onView(withId(R.id.usernameEditText)).perform(click(),typeText("ppp123@gmail.com"), closeSoftKeyboard());
//        onView(withId(R.id.passwordEditText)).perform(typeText("ppp12"), closeSoftKeyboard());
//        onView(withId(R.id.adminCheckBox)).perform(click());
//        onView(withId(R.id.submitButton)).perform(click());
//    }

//    @SmallTest
//    public void clickTextInputLayout(){
//        onView(withId(R.id.usernameEditText)).perform(click(),typeText("zzz11@gmail.com"), closeSoftKeyboard());
//        onView(withId(R.id.passwordEditText)).perform(typeText("zz1122"), closeSoftKeyboard());
//        onView(withId(R.id.adminCheckBox)).perform(click());
//        onView(withId(R.id.submitButton)).perform(click());
//    }


}
