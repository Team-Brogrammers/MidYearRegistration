//package com.example.mid_year_registration;
//
//import android.support.test.filters.SmallTest;
//import android.test.ActivityInstrumentationTestCase2;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//public class AddCoursesTest extends ActivityInstrumentationTestCase2<AddCoursesActivity> {
//
//    AddCoursesActivity activity;
//    private FirebaseAuth mFirebaseAuth;
//
//    public AddCoursesTest(){
//        super(AddCoursesActivity.class);
//    }
//
//    @Override
//    protected void setUp() throws Exception{
//        super.setUp();
//        activity = getActivity();
//    }
//
//    @SmallTest
//    public void testVisibility(){
//        // dismiss the progress dialog
//        if(activity.getmProgressDialog().isShowing()){
//            activity.getmProgressDialog().dismiss();
//        }
//        onView(withId(R.id.tvYourCourses)).check(matches(isDisplayed()));
//        onView(withId(R.id.tvAddCourses)).check(matches(isDisplayed()));
////        onView(withId(R.id.courseSelectionSpinner2)).check(matches(isDisplayed()));
//        onView(withId(R.id.updateButton)).check(matches(isDisplayed()));
//    }
//
//}
