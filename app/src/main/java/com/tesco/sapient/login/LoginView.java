package com.tesco.sapient.login;

import com.tesco.sapient.dto.UserDTO;

/**
 * Created by akhpatil on 3/16/2018.
 */

public interface LoginView {

    void loginSuccess(UserDTO userDTO);

    void loginFailed();

}
