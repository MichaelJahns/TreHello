package com.michaeljahns.tre_hello;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.michaeljahns.tre_hello.tasks.activities.FormActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class CreateTaskTest {

    @Rule
    public ActivityTestRule<FormActivity> activityTestRule = new ActivityTestRule<>(FormActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks(){
        AccessibilityChecks.enable();
    }

    @Test
    public void testLanding(){
        onView(withId(R.id.newTaskName))
                .check(matches(isDisplayed()));
        onView(withId(R.id.newTaskDescription))
                .check(matches(isDisplayed()));
        onView(withId(R.id.newTaskPhase))
                .check(matches(isDisplayed()));
        onView(withId(R.id.submitNewTask))
                .check(matches(isClickable()));
    }

}
