package com.fullstack.labb2messages.dto;


public class FirstMessageDTO {
    private int employeeId;
    private String messageTitle;
    private String message;
    private int userId;

    public FirstMessageDTO(int employeeId, String title, String message, int userId) {
        this.employeeId = employeeId;
        this.messageTitle = title;
        this.message = message;
        this.userId = userId;
    }

    public int getEmplyeeId() {
        return employeeId;
    }

    public void setEmplyeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getTitle() {
        return messageTitle;
    }

    public void setTitle(String title) {
        this.messageTitle = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
