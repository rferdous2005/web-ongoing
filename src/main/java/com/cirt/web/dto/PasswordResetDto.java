package com.cirt.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDto {
    private String username, oldPassword, newPassword, confirmNewPassword;
}
