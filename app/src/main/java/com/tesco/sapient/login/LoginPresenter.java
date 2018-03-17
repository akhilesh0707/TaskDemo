package com.tesco.sapient.login;

import com.tesco.sapient.db.UserRepository;
import com.tesco.sapient.dto.UseDTO;

/**
 * Created by akhpatil on 3/16/2018.
 */

public class LoginPresenter {
    private LoginView view;
    private UserRepository repository;

    public LoginPresenter(LoginView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void login(UseDTO user) {
        UseDTO useDTO = repository.authenticate(user);
        if (useDTO != null) {
            view.loginSuccess();
        } else {
            view.loginFailed();
        }
    }
}
