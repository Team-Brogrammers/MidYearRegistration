package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    LoginActivity activity;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButton)).check(matches(isClickable()));
        onView(withId(R.id.resetPasswordTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.creatAccountTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));

    }

    @SmallTest
    public void testEmailValidation(){
        assertEquals(activity.isValidEmail("1234@g"), false);
        assertEquals(activity.isValidEmail("1234567@wits.ac.za"), true);
    }

    @SmallTest
    public void testPasswordValidation(){
        assertEquals(activity.isValidPassword(null), false);
        assertEquals(activity.isValidPassword("abc"), false);
        assertEquals(activity.isValidPassword("goodPassword1"), true);
    }

//    @SmallTest
//    public void testValidInput(){
//        onView(withId(R.id.emailEditText)).perform(typeText("123456@students.wits.ac.za"), closeSoftKeyboard());
//        onView(withId(R.id.passwordEditText)).perform(typeText("furry123456"), closeSoftKeyboard());
//        //onView(withId(R.id.loginButton)).perform(click());
//    }

//    @SmallTest
//    public void testValidInput2(){
//        onView(withId(R.id.emailEditText)).perform(typeText("123456@wits.ac.za"), closeSoftKeyboard());
//        onView(withId(R.id.passwordEditText)).perform(typeText("testing2"), closeSoftKeyboard());
//        //onView(withId(R.id.loginButton)).perform(click());
//    }

//    @SmallTest
//    public void testValidInput3(){
//        onView(withId(R.id.emailEditText)).perform(typeText("123456@student.wits.ac.za"), closeSoftKeyboard());
//        onView(withId(R.id.passwordEditText)).perform(typeText("testing0"), closeSoftKeyboard());
//        //onView(withId(R.id.loginButton)).perform(click());
//    }
//
//    @SmallTest
//    public void testNoPassword(){
//        onView(withId(R.id.emailEditText)).perform(typeText("invalidaccount@gmail.com"), closeSoftKeyboard());
//        //onView(withId(R.id.loginButton)).perform(click());
//    }

//    @SmallTest
//    public void testNoEmail(){
//        onView(withId(R.id.passwordEditText)).perform(typeText("password12345"), closeSoftKeyboard());
//        //onView(withId(R.id.loginButton)).perform(click());
//    }
//
//    @SmallTest
//    public void testInvalidEmail(){
//        onView(withId(R.id.emailEditText)).perform(typeText("invalidaccount"), closeSoftKeyboard());
//        onView(withId(R.id.passwordEditText)).perform(typeText("password12345"), closeSoftKeyboard());
//        //onView(withId(R.id.loginButton)).perform(click());
//    }

}