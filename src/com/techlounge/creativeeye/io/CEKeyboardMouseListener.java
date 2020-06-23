package com.techlounge.creativeeye.io;

public interface CEKeyboardMouseListener {
    void onKeyboard(CEInput.Key key, int scancode, CEInput.KeyAction action, CEInput.Mod[] mods);
    void onMouse(CEInput.MouseButton button, CEInput.MouseButtonAction action, CEInput.Mod[] mods, double xPos, double yPos);
    void onScroll(double xOffset, double yOffset);
}
