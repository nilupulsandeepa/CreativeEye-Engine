package com.techlounge.creativeeye.error;

public enum CEErrorCode {
    GLFW_INIT_FAILED("GLFW : Initialization Failed"),
    GLFW_CREATE_WINDOW_FAILED("GLFW : Cannot create window"),
    GLFW_MONITOR_PROPERTIES_UNAVAILABLE("GLFW : Cannot get monitor properties"),

    JAVA_CANNOT_READ_SHADER_FILE("CEEngine : Cannot read shader file"),
    JAVA_CANNOT_READ_PROPERTIES_FILE("CEEngine : Cannot read properties file"),
    JAVA_CANNOT_READ_PROPERTY("CEEngine : Cannot read property");

    public String errorMessage;

    CEErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
