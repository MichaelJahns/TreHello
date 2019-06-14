package com.michaeljahns.tre_hello;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.michaeljahns.tre_hello.tasks.activities.TaskStreamActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class TaskFeedTest {

    @Rule
    public ActivityTestRule<TaskStreamActivity> activityTestRule = new ActivityTestRule<>(TaskStreamActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks(){
        AccessibilityChecks.enable();
    }

    @Test
    public void testLanding(){
        onView(withId(R.id.taskRecycler))
                .check(matches(isDisplayed()));
        onView(withId(R.id.courierNewTask))
                .check(matches(isClickable()));
    }


}