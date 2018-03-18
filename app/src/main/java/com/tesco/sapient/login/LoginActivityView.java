package com.tesco.sapient.login;

import com.tesco.sapient.dto.UserDTO;

/**
 * LoginActivityView
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public interface LoginActivityView {

    void loginSuccess(UserDTO userDTO);

    void loginFailed();

}
