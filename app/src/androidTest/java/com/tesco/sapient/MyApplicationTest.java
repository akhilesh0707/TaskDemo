package com.tesco.sapient;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.tesco.sapient.dto.UserDTO;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by akhpatil on 3/19/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MyApplicationTest {

    @Test
    public void shouldSetUserObjectToMyApplication() throws Exception {
        UserDTO userDTO = new UserDTO();
        MyApplication.get(InstrumentationRegistry.getTargetContext()).setUser(userDTO);
    }

    @Test
    public void shouldGetUserObjectFromMyApplication() throws Exception {
        UserDTO userDTO = MyApplication.get(InstrumentationRegistry.getTargetContext()).getUser();
        assertNotNull(userDTO);
    }
}
