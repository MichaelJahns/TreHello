package com.michaeljahns.tre_hello;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import com.michaeljahns.tre_hello.tasks.activities.EditTaskActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class EditTaskTest {

    @Rule
    public ActivityTestRule<EditTaskActivity> activityTestRule = new ActivityTestRule<>(EditTaskActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks(){
        AccessibilityChecks.enable();
    }

    @Test
    public void testLanding(){
        onView(withId(R.id.viewTaskName))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editTaskDescription))
                .check(matches(isDisplayed()));
        onView(withId(R.id.spinnerEditTaskSpinner))
                .check(matches(isDisplayed()));
        onView(withId(R.id.updateTask))
                .check(matches(isClickable()));
    }
}
