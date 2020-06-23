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

    private int frames;
    private static long time;

    public CEWindow(int windowWidth, int windowHeight) {
        System.out.println("Window Initialization");
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowTitle = "Creative Eye";
    }

    public CEWindow(int windowWidth, int windowHeight, String windowTitle) {
        this(windowWidth, windowHeight);
        this.windowTitle = windowTitle;
    }

    public void create() throws CEException {

        //Setting GLFW error callback for development
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW. Return false if not initialized
        if(!GLFW.glfwInit()) {
            //Throws an exception if glfw not initialized
            throw new CEWindowException(ErrorCode.GLFW_INIT_FAILED.errorMessage);
        }

        //Get primary monitor
        long primaryMonitor = GLFW.glfwGetPrimaryMonitor();

        //Creating GLFW Window
        this.window = GLFW.glfwCreateWindow(this.windowWidth, this.windowHeight, this.windowTitle, 0, 0);
        //If window not created throw an exception
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
        //FPS counter. Will be removed later
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(window, this.windowTitle + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
        GLFW.glfwPollEvents();
    }

    public void render() {
        //Swapping front and back buffers
        GLFW.glfwSwapBuffers(this.window);
    }

    public void setKeyboardMouseListener(CEKeyboardMouseListener keyboardMouseListener) {
        this.keyboardCallback = new CEKeyboard();
        this.keyboardCallback.setKeyboardMouseListener(keyboardMouseListener);
        this.mouseCallback = new CEMouse();
        this.mouseCallback.setKeyboardMouseListener(keyboardMouseListener);

        //Setting up Keyboard, Mouse Buttons and Mouse Position callbacks for the window
        GLFW.glfwSetKeyCallback(this.window, this.keyboardCallback);
        GLFW.glfwSetMouseButtonCallback(this.window, this.mouseCallback.getMouseButtonCallback());
        GLFW.glfwSetCursorPosCallback(this.window, this.mouseCallback.getCursorPosCallback());
        GLFW.glfwSetScrollCallback(this.window, this.mouseCallback.getScrollCallback());
    }

    public boolean shouldWindowClose() {
        //Check if window should close or not
        return GLFW.glfwWindowShouldClose(this.window);
    }

    public void close() {
        //Releasing input instances
        this.keyboardCallback.release();
        this.mouseCallback.release();
        //Marking window to be closed
        GLFW.glfwSetWindowShouldClose(this.window, true);
    }
}
