package com.techlounge.creativeeye.error;

public enum  ErrorCode {
    GLFW_INIT_FAILED("GLFW : Initialization Failed"),
    GLFW_CREATE_WINDOW_FAILED("GLFW : Cannot create window"),
    GLFW_MONITOR_PROPERTIES_UNAVAILABLE("GLFW : Cannot get monitor properties");

    public String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
