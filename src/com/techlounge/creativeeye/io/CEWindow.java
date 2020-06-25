package com.techlounge.creativeeye.io;

import com.techlounge.creativeeye.error.CEException;
import com.techlounge.creativeeye.error.CEWindowException;
import com.techlounge.creativeeye.error.ErrorCode;
import org.lwjgl.glfw.*;

public class CEWindow {

    //Window width and height
    private int windowWidth;
    private int windowHeight;

    //Window position
    private int windowPosX;
    private int windowPosY;

    //Window title
    private String windowTitle;

    //Window
    private long window;

    //Window, Keyboard, Mouse callbacks
    private CEKeyboard keyboardCallback;
    private CEMouse mouseCallback;

    private GLFWWindowSizeCallback windowSizeCallback;
    private GLFWWindowPosCallback windowPosCallback;

    //Primary monitor
    private long primaryMonitor;
    private GLFWVidMode primaryMonitorVideoMode;

    //Check if in fullscreen
    private boolean isFullscreen = false;

    private int frames;
    private static long time;

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

        //Setting GLFW error callback for development
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW. Return false if not initialized
        if(!GLFW.glfwInit()) {
            //Throws an exception if glfw not initialized
            throw new CEWindowException(ErrorCode.GLFW_INIT_FAILED.errorMessage);
        }

        //Get primary monitor
        this.primaryMonitor = GLFW.glfwGetPrimaryMonitor();

        //Creating GLFW Window
        this.window = GLFW.glfwCreateWindow(this.windowWidth, this.windowHeight, this.windowTitle, 0, 0);
        //If window not created throw an exception
        if (this.window == 0) {
            GLFW.glfwTerminate();
            throw new CEWindowException(ErrorCode.GLFW_CREATE_WINDOW_FAILED.errorMessage);
        }

        //Get monitor properties
        this.primaryMonitorVideoMode = GLFW.glfwGetVideoMode(primaryMonitor);
        if (primaryMonitorVideoMode != null) {
            //Positioning game window at center of the monitor
            this.windowPosX = (this.primaryMonitorVideoMode.width() - this.windowWidth) / 2;
            this.windowPosY = (this.primaryMonitorVideoMode.height() - this.windowHeight) / 2;
            GLFW.glfwSetWindowPos(this.window, this.windowPosX, this.windowPosY);

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

    public void setWindowSizeCallback(CEWindowResizeCallback windowResizeCallback) {
        this.windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                if (!isFullscreen) {
                    windowWidth = width;
                    windowHeight = height;
                }
                windowResizeCallback.onResize(width, height);
            }
        };

        this.windowPosCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                if (!isFullscreen) {
                    windowPosX = xpos;
                    windowPosY = ypos;
                }
            }
        };

        GLFW.glfwSetWindowSizeCallback(this.window, this.windowSizeCallback);
        GLFW.glfwSetWindowPosCallback(this.window, this.windowPosCallback);
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

    public void toggleFullScreen() {
        this.isFullscreen = !this.isFullscreen;
        if (this.isFullscreen) {
            GLFW.glfwSetWindowMonitor(this.window, this.primaryMonitor, 0, 0, this.primaryMonitorVideoMode.width(), this.primaryMonitorVideoMode.height(), this.primaryMonitorVideoMode.refreshRate());
        } else {
            GLFW.glfwSetWindowMonitor(this.window, 0, this.windowPosX, this.windowPosY, this.windowWidth, this.windowHeight, this.primaryMonitorVideoMode.refreshRate());
        }
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

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }
}
