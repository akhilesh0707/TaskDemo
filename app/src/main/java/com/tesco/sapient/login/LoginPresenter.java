package com.tesco.sapient.login;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.dto.UseDTO;

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

    public void login(UseDTO user) {
        UseDTO useDTO = dataManager.authenticate(user);
        if (useDTO != null) {
            view.loginSuccess();
        } else {
            view.loginFailed();
        }
    }
}
