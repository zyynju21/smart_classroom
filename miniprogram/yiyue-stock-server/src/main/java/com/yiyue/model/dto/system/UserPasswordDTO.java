package com.yiyue.model.dto.system;

import lombok.Data;

@Data
public class UserPasswordDTO {
    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}
