package com.techlounge.creativeeye.io;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class CEMouse {

    public enum Action {
        BUTTON_DOWN,
        BUTTON_UP,
        BUTTON_NO_ACTION;

        public static Action getAction(int action) {
            return switch (action) {
                case 0 -> BUTTON_UP;
                case 1 -> BUTTON_DOWN;
                default -> BUTTON_NO_ACTION;
            };
        }
    }

    public enum Button {
        MOUSE_LEFT,
        MOUSE_RIGHT,
        MOUSE_MIDDLE,
        MOUSE_NO_BUTTON;

        public static Button getButton(int button) {
            return switch (button) {
                case 0 -> MOUSE_LEFT;
                case 1 -> MOUSE_RIGHT;
                case 2 -> MOUSE_MIDDLE;
                default -> MOUSE_NO_BUTTON;
            };
        }
    }

    public enum Mod {
        SHIFT_MOD,
        CONTROL_MOD,
        ALT_MOD,
        NO_MOD;

        public static Mod[] getMods(int mods) {
            return switch (mods) {
                case 1 -> new Mod[] {SHIFT_MOD};
                case 2 -> new Mod[] {CONTROL_MOD};
                case 3 -> new Mod[] {SHIFT_MOD, CONTROL_MOD};
                case 4 -> new Mod[] {ALT_MOD};
                case 5 -> new Mod[] {SHIFT_MOD, ALT_MOD};
                case 6 -> new Mod[] {CONTROL_MOD, ALT_MOD};
                case 7 -> new Mod[] {SHIFT_MOD, CONTROL_MOD, ALT_MOD};
                default -> new Mod[] {NO_MOD};
            };
        }
    }

    private CEKeyboardMouseListener keyboardMouseListener;

    private int clickButton = 3;
    private int clickAction = 2;
    private int clickMods = 0;
    private double mouseXPos = 0;
    private double mouseYPos = 0;

    public GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            clickButton = button;
            clickAction = action;
            clickMods = mods;
            keyboardMouseListener.onMouse(Button.getButton(clickButton), Action.getAction(clickAction), Mod.getMods(clickMods), mouseXPos, mouseYPos);
        }
    };

    public GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            mouseXPos = xpos;
            mouseYPos = ypos;
            if (clickAction == 0) {
                clickAction = 2;
                clickButton = 3;
                clickMods = 0;
            }
            keyboardMouseListener.onMouse(Button.getButton(clickButton), Action.getAction(clickAction), Mod.getMods(clickMods), mouseXPos, mouseYPos);
        }
    };

    public void setKeyboardMouseListener(CEKeyboardMouseListener keyboardMouseListener) {
        this.keyboardMouseListener = keyboardMouseListener;
    }
}
