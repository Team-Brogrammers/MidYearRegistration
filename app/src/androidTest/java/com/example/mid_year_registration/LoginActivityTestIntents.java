package com.example.mid_year_registration;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTestIntents {
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    @SmallTest
    public void testSignUp(){
        onView(withId(R.id.creatAccountTextView)).perform(click());
        //intended(toPackage("com.example.mid_year_registration.SignUpActivity"));

    }

   @Test
   @SmallTest
   public void testResetPassword(){
        onView(withId(R.id.resetPasswordTextView)).perform(click());
       //intended(toPackage("com.example.mid_year_registration/LoginActivity.java"));
   }
}
