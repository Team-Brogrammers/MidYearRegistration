package com.example.mid_year_registration;

import android.support.test.filters.MediumTest;
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
    public void testVisibility(){
        onView(withId(R.id.imageView3)).check(matches(isDisplayed()));
        onView(withId(R.id.usernameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.submitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.adminCheckBox)).check(matches(isClickable()));
        onView(withId(R.id.submitButton)).check(matches(isClickable()));
    }

    @SmallTest
    public void testBackButton(){
        assertEquals(activity.onSupportNavigateUp(), true);
    }

//   @MediumTest
//   public void testNoEmailNoPassword(){
//      onView(withId(R.id.usernameEditText)).perform(typeText("  "), closeSoftKeyboard());
//      onView(withId(R.id.passwordEditText)).perform(typeText("  "), closeSoftKeyboard());
////      onView(withId(R.id.submitButton)).perform(click());
//   }

    @MediumTest
   public void testNoEmail(){
        onView(withId(R.id.usernameEditText)).perform(typeText("  "), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("abcdefg12345"), closeSoftKeyboard());
//        onView(withId(R.id.submitButton)).perform(click());
   }


   @MediumTest
   public void testValidStudent(){
        onView(withId(R.id.usernameEditText)).perform(typeText("1234567@students.wits.ac.za"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("studentswits"), closeSoftKeyboard());
//       onView(withId(R.id.submitButton)).perform(click());
   }

   @MediumTest
   public void testValidCoordinator(){
      onView(withId(R.id.usernameEditText)).perform(typeText("123456@wits.ac.za"), closeSoftKeyboard());
       onView(withId(R.id.passwordEditText)).perform(typeText("staffwits"), closeSoftKeyboard());
//       onView(withId(R.id.submitButton)).perform(click());
   }

}
