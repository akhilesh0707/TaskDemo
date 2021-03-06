package com.tesco.sapient.login;

import com.tesco.sapient.R;
import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.dto.UserDTO;

/**
 * LoginActivity Presenter to manager view and business login (Model)
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class LoginPresenter {
    private LoginView view;
    private DataManager dataManager;

    /**
     * LoginPresenter Constructor
     *
     * @param view
     * @param dataManager
     */
    public LoginPresenter(LoginView view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }

    /**
     * LoginActivity method to check user exists or not in Database
     */
    public void login() {
        String username = view.getUsername();

        if (username.isEmpty()) {
            view.showUsernameErrorMessage(R.string.login_validation_error_message_username);
            return;
        }

        String password = view.getPassword();
        if (password.isEmpty()) {
            view.showPasswordErrorMessage(R.string.login_validation_error_message_password);
            return;
        }

        UserDTO userDTO = dataManager.authenticate(username,password);
        if (userDTO != null) {
            view.loginSuccess(userDTO);
        } else {
            view.loginFailed();
        }
        return;

    }
}
