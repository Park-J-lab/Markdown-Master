package com.markdownMaster.auth.services;

import com.markdownMaster.auth.dtos.UserInfoDTO;
import com.markdownMaster.auth.dtos.UserLoginDTO;

public interface UserService {

    //     user creation
    void createUser(UserInfoDTO userInfoDTO);

    //    user fetching
//    UserInfoDTO retrieveUserInfo(String userId, String token);
    UserInfoDTO retrieveUserInfo(String userId);

    //    user login
    UserInfoDTO loginUser(UserLoginDTO userLoginDTO);
}

