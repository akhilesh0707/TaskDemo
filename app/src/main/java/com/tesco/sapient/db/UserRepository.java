package com.tesco.sapient.db;

import com.tesco.sapient.dto.UserDTO;

/**
 * Created by akhpatil on 3/16/2018.
 */

public interface UserRepository {

    UserDTO authenticate(UserDTO userDTO);


}
