package com.cenfotec.p3.neuralforge_api.model.resource;

public class PasswordResetResource {
    private String userId;
    private String newPassword;

    // Getters y setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
