package com.tesco.sapient.db;

import com.tesco.sapient.dto.UseDTO;

/**
 * Created by akhpatil on 3/16/2018.
 */

public interface UserRepository {

    UseDTO authenticate(UseDTO useDTO);


}
