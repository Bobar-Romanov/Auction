package com.auction.auction.forms;


import javax.validation.constraints.*;

public class UserForm {

    @Email
    private String email;

    @NotBlank
    @Min(4)
    @Max(20)
    private String username;

    @NotBlank
    @Min(8)
    @Max(20)
    private String password;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        this.username = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
