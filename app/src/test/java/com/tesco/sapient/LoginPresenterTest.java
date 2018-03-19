package com.tesco.sapient;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.login.LoginView;
import com.tesco.sapient.login.LoginPresenter;
import com.tesco.sapient.dto.UserDTO;

import org.junit.After;
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
public class LoginPresenterTest {

    @Mock
    LoginView view;
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
    public void shouldShowUsernameErrorMessageIsEmpty() {
        //Given
        when(view.getUsername()).thenReturn("");
        //When
        presenter.login();
        //Then
        verify(view).showUsernameErrorMessage(R.string.login_validation_error_message_username);
    }

    @Test
    public void shouldShowPasswordErrorMessageIsEmpty() {
        //Given
        when(view.getUsername()).thenReturn("sid");
        when(view.getPassword()).thenReturn("");
        //When
        presenter.login();
        //Then
        verify(view).showPasswordErrorMessage(R.string.login_validation_error_message_password);
    }

    @Test
    public void shouldShowMainActivityWhenUsernameAndPasswordIsCorrect() throws Exception {
        //Given
        when(view.getUsername()).thenReturn("sid");
        when(view.getPassword()).thenReturn("sid");
        when(dataManager.authenticate("sid", "sid")).thenReturn(user);
        //When
        presenter.login();
        //Then
        verify(view).loginSuccess(user);
    }

    @Test
    public void shouldShowLoginErrorWhenUsernameAndPasswordAreInvalid() throws Exception {
        //Given
        when(view.getUsername()).thenReturn("sid");
        when(view.getPassword()).thenReturn("sid");
        when(dataManager.authenticate("sid", "sid")).thenReturn(null);
        //When
        presenter.login();
        //Then
        verify(view).loginFailed();
    }


    @After
    public void tearDown() throws Exception {
        presenter = null;
        user = null;
    }


}
