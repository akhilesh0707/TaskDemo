package com.tesco.sapient.login;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.dto.UserDTO;

/**
 * Created by akhpatil on 3/16/2018.
 */

public class LoginPresenter {
    private LoginView view;
    private DataManager dataManager;

    public LoginPresenter(LoginView view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }

    public void login(UserDTO user) {
        UserDTO userDTO = dataManager.authenticate(user);
        if (userDTO != null) {
            view.loginSuccess(userDTO);
        } else {
            view.loginFailed();
        }
    }
}
