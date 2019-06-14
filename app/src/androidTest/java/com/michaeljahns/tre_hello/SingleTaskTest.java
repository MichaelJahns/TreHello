package com.michaeljahns.tre_hello;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.michaeljahns.tre_hello.tasks.activities.SingleTaskActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SingleTaskTest {

    @Rule
    public ActivityTestRule<SingleTaskActivity> activityTestRule = new ActivityTestRule<>(SingleTaskActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks(){
        AccessibilityChecks.enable();
    }

    @Test
    public void testLanding(){
        onView(withId(R.id.toggleEditMode))
                .check(matches(isClickable()));
        onView(withId(R.id.toggleTeamActions))
                .check(matches(isClickable()));
        onView(withId(R.id.courierNewLog))
                .check(matches(isClickable()));
        onView(withId(R.id.recyclerAssignedUsers))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recyclerTaskLog))
                .check(matches(isDisplayed()));
        onView(withId(R.id.singleViewTaskName))
                .check(matches(isDisplayed()));
        onView(withId(R.id.singleViewTaskTeam))
                .check(matches(isDisplayed()));
        onView(withId(R.id.singleViewTaskDescription))
                .check(matches(isDisplayed()));
        onView(withId(R.id.singleViewTaskPhase))
                .check(matches(isDisplayed()));
    }

}
