package com.michaeljahns.tre_hello;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainTest {
    @BeforeClass
    public static void enableAccessibilityChecks(){
        AccessibilityChecks.enable();
    }

}
