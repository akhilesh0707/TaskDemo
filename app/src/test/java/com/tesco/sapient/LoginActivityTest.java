package com.tesco.sapient;

import android.app.Application;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.db.UserRepository;
import com.tesco.sapient.di.component.ActivityComponent;
import com.tesco.sapient.di.component.ApplicationComponent;
import com.tesco.sapient.di.component.DaggerActivityComponent;
import com.tesco.sapient.di.module.ActivityModule;
import com.tesco.sapient.login.LoginActivity;
import com.tesco.sapient.login.LoginPresenter;
import com.tesco.sapient.login.LoginView;
import com.tesco.sapient.dto.UseDTO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;

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
    DataManager dataManager;
    private LoginPresenter presenter;
    private UseDTO user;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, dataManager);
        user = new UseDTO("sid", "sid", 9901, "Record Wastage Thee");
    }

    @Test
    public void userSuccessfullyLoginUsingDummyRecord() {
        //Given
        when(dataManager.authenticate(user)).thenReturn(user);
        //When
        presenter.login(user);
        //Then
        verify(view).loginSuccess();
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
