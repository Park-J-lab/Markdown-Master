package com.markdownMaster.auth.dtos;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String username;
    private String password;
}
