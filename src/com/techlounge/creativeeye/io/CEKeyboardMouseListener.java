package com.techlounge.creativeeye.io;

public interface CEKeyboardMouseListener {
    void onKeyboard(int key, int scancode, int action, int mods);
    void onMouse(CEMouse.Button button, CEMouse.Action action, CEMouse.Mod[] mods, double xPos, double yPos);
}
