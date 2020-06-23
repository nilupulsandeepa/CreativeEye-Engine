package com.techlounge.creativeeye.io;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.HashMap;

public class CEKeyboard extends GLFWKeyCallback {

    private CEKeyboardMouseListener keyboardMouseListener;

    private final HashMap<Integer, CEInput.Key> keyMap = new HashMap<>(CEInput.Key.values().length);

    public CEKeyboard() {
        //Initializing Key Map
        for (CEInput.Key key : CEInput.Key.values()) {
            keyMap.put(key.keyCode, key);
        }
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        this.keyboardMouseListener.onKeyboard(keyMap.get(key), scancode, CEInput.KeyAction.getKeyAction(action), CEInput.Mod.getMods(mods));
    }

    public void setKeyboardMouseListener(CEKeyboardMouseListener keyboardMouseListener) {
        this.keyboardMouseListener = keyboardMouseListener;
    }

    public void release() {
        this.free();
    }
}
