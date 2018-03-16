package com.tesco.sapient;

import com.tesco.sapient.db.UserRepository;
import com.tesco.sapient.login.LoginPresenter;
import com.tesco.sapient.login.LoginView;
import com.tesco.sapient.model.UserModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by akhpatil on 3/16/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginActivityTest {

    @Mock
    LoginView view;

    @Mock
    UserRepository repository;

    @Test
    public void testShouldPass() {
        assertEquals(1, 1);
    }

    @Test
    public void userSuccessfullyLoginUsingDummyRecord() {
        //Given
        UserModel user = new UserModel("sid", "sid");
        LoginPresenter presenter = new LoginPresenter(view, repository);
        presenter.login(user);
        //Then
        when(repository.authenticate(user)).thenReturn(user);
        //When
        verify(view).loginSuccess();
    }


}
