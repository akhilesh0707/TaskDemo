package com.tesco.sapient.login;

import com.tesco.sapient.dto.UserDTO;

/**
 * LoginView
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public interface LoginView {

    String getUsername();

    void showUsernameErrorMessage(int resId);

    String getPassword();

    void showPasswordErrorMessage(int resId);

    void loginSuccess(UserDTO userDTO);

    void loginFailed();

}
