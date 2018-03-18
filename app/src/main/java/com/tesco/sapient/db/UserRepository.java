package com.tesco.sapient.db;

import com.tesco.sapient.dto.UserDTO;

/**
 * UserRepository interface to manage user Database operation
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public interface UserRepository {
    UserDTO authenticate(String username, String password);
}
