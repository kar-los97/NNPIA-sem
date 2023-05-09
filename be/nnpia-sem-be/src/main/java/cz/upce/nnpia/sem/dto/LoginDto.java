package cz.upce.nnpia.sem.dto;

import cz.upce.nnpia.sem.entity.Role;

public class LoginDto {

    private String email;

    private String password;

    private String token;

    private Role role;

    public LoginDto(String email, String password, String token, Role role) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
    }

    public LoginDto(String email, String token, Role role) {
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
