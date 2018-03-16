package com.tesco.sapient.db;

import com.tesco.sapient.model.UserModel;

/**
 * Created by akhpatil on 3/16/2018.
 */

public interface UserRepository {

    UserModel authenticate(UserModel userModel);


}
