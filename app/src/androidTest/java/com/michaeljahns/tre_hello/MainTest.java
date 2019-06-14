package com.michaeljahns.tre_hello;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks(){
        AccessibilityChecks.enable();
    }

    @Test
    public void testLanding(){
        onView(withId(R.id.toggleLogActions))
                .check(matches(isDisplayed()));
        onView(withId(R.id.viewDisplayName))
                .check(matches(withSubstring("Tre-Hello!")));
        // A bad test because neither of these are clickable when a user is not logged in
        onView(withId(R.id.courierNewTask))
                .check(matches(isClickable()));
        onView(withId(R.id.courierProfileEdit))
                .check(matches(isClickable()));
    }

    @Test
    public void testLandingMessageChangesBetweenLogs(){

    }
}
