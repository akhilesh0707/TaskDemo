package com.tesco.sapient.login;

import com.tesco.sapient.db.UserRepository;
import com.tesco.sapient.model.UserModel;

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

    public void login(UserModel user) {
        UserModel userModel = repository.authenticate(user);
        if (userModel != null) {
            view.loginSuccess();
        } else {
            view.loginFailed();
        }
    }
}
