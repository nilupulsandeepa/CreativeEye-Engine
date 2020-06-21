package com.techlounge.creativeeye.io;

import com.techlounge.creativeeye.error.CEErrorCallback;
import com.techlounge.creativeeye.error.CEException;
import com.techlounge.creativeeye.error.CEWindowException;
import com.techlounge.creativeeye.error.ErrorCode;
import org.lwjgl.glfw.*;

public class CEWindow {

    //Window width and height
    private int windowWidth;
    private int windowHeight;

    //Window title
    private String windowTitle;

    //Window
    private long window;

    //Window Keyboard, Mouse callbacks
    private CEKeyboard keyboardCallback;
    private CEMouse mouseCallback;

    public CEWindow(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowTitle = "Creative Eye";
    }

    public CEWindow(int windowWidth, int windowHeight, String windowTitle) {
        this(windowWidth, windowHeight);
        this.windowTitle = windowTitle;
    }

    public void create() throws CEException {

        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW. Return false if not initialized
        if(!GLFW.glfwInit()) {
            throw new CEWindowException(ErrorCode.GLFW_INIT_FAILED.errorMessage);
        }

        //Get primary monitor
        long primaryMonitor = GLFW.glfwGetPrimaryMonitor();

        //Creating GLFW Window
        this.window = GLFW.glfwCreateWindow(this.windowWidth, this.windowHeight, this.windowTitle, 0, 0);
        //If window not created
        if (this.window == 0) {
            GLFW.glfwTerminate();
            throw new CEWindowException(ErrorCode.GLFW_CREATE_WINDOW_FAILED.errorMessage);
        }

        //Get monitor properties
        GLFWVidMode primaryMonitorVideoMode = GLFW.glfwGetVideoMode(primaryMonitor);
        if (primaryMonitorVideoMode != null) {
            //Positioning game window at center of the monitor
            GLFW.glfwSetWindowPos(this.window, (primaryMonitorVideoMode.width() - this.windowWidth) / 2, (primaryMonitorVideoMode.height() - this.windowHeight) / 2);

        } else {
            GLFW.glfwTerminate();
            GLFW.glfwDestroyWindow(this.window);
            throw new CEWindowException(ErrorCode.GLFW_MONITOR_PROPERTIES_UNAVAILABLE.errorMessage);
        }

        GLFW.glfwMakeContextCurrent(this.window);

        //Wait for screen to finish update before calling swapBuffers
        GLFW.glfwSwapInterval(1);

        //Showing created window
        GLFW.glfwShowWindow(this.window);
    }

    public void updateEvents() {
        GLFW.glfwPollEvents();
    }

    public void render() {
        GLFW.glfwSwapBuffers(this.window);
    }

    public void setKeyboardMouseListener(CEKeyboardMouseListener keyboardMouseListener) {
        this.keyboardCallback = new CEKeyboard();
        this.keyboardCallback.setKeyboardMouseListener(keyboardMouseListener);
        this.mouseCallback = new CEMouse();
        this.mouseCallback.setKeyboardMouseListener(keyboardMouseListener);

        GLFW.glfwSetKeyCallback(this.window, this.keyboardCallback);
        GLFW.glfwSetMouseButtonCallback(this.window, this.mouseCallback.mouseButtonCallback);
        GLFW.glfwSetCursorPosCallback(this.window, this.mouseCallback.cursorPosCallback);
    }

    public boolean shouldWindowClose() {
        return GLFW.glfwWindowShouldClose(this.window);
    }

    public void close() {
        GLFW.glfwSetWindowShouldClose(this.window, true);
    }
}
