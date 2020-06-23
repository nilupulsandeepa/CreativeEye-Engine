package com.techlounge.creativeeye.io;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class CEMouse {

    private CEKeyboardMouseListener keyboardMouseListener;

    private int clickButton = 3;
    private int clickAction = 2;
    private int clickMods = 0;
    private double mouseXPos = 0;
    private double mouseYPos = 0;

    private final GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            clickButton = button;
            clickAction = action;
            clickMods = mods;
            keyboardMouseListener.onMouse(CEInput.MouseButton.getButton(clickButton), CEInput.MouseButtonAction.getAction(clickAction), CEInput.Mod.getMods(clickMods), mouseXPos, mouseYPos);
        }
    };

    private final GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            mouseXPos = xpos;
            mouseYPos = ypos;
            if (clickAction == 0) {
                clickAction = 2;
                clickButton = 3;
                clickMods = 0;
            }
            keyboardMouseListener.onMouse(CEInput.MouseButton.getButton(clickButton), CEInput.MouseButtonAction.getAction(clickAction), CEInput.Mod.getMods(clickMods), mouseXPos, mouseYPos);
        }
    };

    private final GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
        @Override
        public void invoke(long window, double xoffset, double yoffset) {
            keyboardMouseListener.onScroll(xoffset, yoffset);
        }
    };

    public void setKeyboardMouseListener(CEKeyboardMouseListener keyboardMouseListener) {
        this.keyboardMouseListener = keyboardMouseListener;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public void release() {
        this.cursorPosCallback.free();
        this.mouseButtonCallback.free();
        this.scrollCallback.free();
    }
}
