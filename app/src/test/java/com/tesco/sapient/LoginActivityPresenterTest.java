package com.tesco.sapient;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.login.LoginActivityView;
import com.tesco.sapient.login.LoginPresenter;
import com.tesco.sapient.dto.UserDTO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by akhpatil on 3/16/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginActivityPresenterTest {

    @Mock
    LoginActivityView view;
    @Mock
    DataManager dataManager;
    private LoginPresenter presenter;
    private UserDTO user;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, dataManager);
        user = new UserDTO("sid", "sid", 9901, "Record Wastage Thee");
    }

    @Test
    public void userSuccessfullyLoginUsingDummyRecord() {
        //Given
        when(dataManager.authenticate(user)).thenReturn(user);
        //When
        presenter.login(user);
        //Then
        verify(view).loginSuccess(user);
    }

    @Test
    public void useFailLoginUsingDummyRecord() {
        //Given
        when(dataManager.authenticate(user)).thenReturn(null);
        //When
        presenter.login(user);
        //Then
        verify(view).loginFailed();
    }


}
