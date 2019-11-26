package com.sanght.shapechallenge.web.vmodel;

import com.sanght.shapechallenge.common.constant.ValidationConstant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginVM {
    @NotNull
    @Size(min = ValidationConstant.USERNAME_MIN_LENGTH, max = ValidationConstant.USERNAME_MAX_LENGTH)
    private String username;

    @NotNull
    @Size(min = ValidationConstant.PASSWORD_MIN_LENGTH, max = ValidationConstant.PASSWORD_MAX_LENGTH)
    private String password;

    private boolean rememberMe = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginVM{" +
                "username='" + username + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
