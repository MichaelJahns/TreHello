package com.michaeljahns.tre_hello;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.michaeljahns.tre_hello.user.UserProfileActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class ViewProfileTest {
    @Rule
    public ActivityTestRule<UserProfileActivity> activityTestRule = new ActivityTestRule<>(UserProfileActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable();
    }

    @Test
    public void testLanding() {
        onView(withId(R.id.viewPreferredName))
                .check(matches(isDisplayed()));
        onView(withId(R.id.viewBiography))
                .check(matches(isDisplayed()));
        onView(withId(R.id.viewSignSpinner))
                .check(matches(isDisplayed()));
    }
}
