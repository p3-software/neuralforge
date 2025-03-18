package com.cenfotec.p3.neuralforge_api.model.resource;

public class PasswordResetResource {
    private String token;
    private String newPassword;

    // Constructor vacío (necesario para la deserialización JSON)
    public PasswordResetResource() {}

    // Constructor con parámetros
    public PasswordResetResource(String token, String newPassword) {
        this.token = token;
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}