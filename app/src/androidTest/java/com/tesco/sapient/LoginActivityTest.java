package com.tesco.sapient;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tesco.sapient.login.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Aki on 3/18/2018.
 */
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity activity = null;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }

    @Test
    public void testLaunchActivity() {
        View view = activity.findViewById(R.id.buttonLogin);
        assertNotNull(view);
    }

    @Test
    public void shouldSetUsernameAndPasswordAndClickLoginButton() {

        assertNotNull(activity.findViewById(R.id.editTextUserName));
        onView(withId(R.id.editTextUserName)).perform(replaceText("sid"));

        assertNotNull(activity.findViewById(R.id.editTextPassword));
        onView(withId(R.id.editTextPassword)).perform(replaceText("sid"));

        //assertNotNull(activity.findViewById(R.id.buttonLogin));
        //onView(withId(R.id.buttonLogin)).perform(click());
    }


    @After
    public void tearDown() throws Exception {
        activity = null;
    }

}