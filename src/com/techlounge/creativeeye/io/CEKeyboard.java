package com.techlounge.creativeeye.io;

import org.lwjgl.glfw.GLFWKeyCallback;

public class CEKeyboard extends GLFWKeyCallback {

    private CEKeyboardMouseListener keyboardMouseListener;

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        this.keyboardMouseListener.onKeyboard(key, scancode, action, mods);
    }

    public void setKeyboardMouseListener(CEKeyboardMouseListener keyboardMouseListener) {
        this.keyboardMouseListener = keyboardMouseListener;
    }

    public void destroy() {
        this.free();
    }
}
