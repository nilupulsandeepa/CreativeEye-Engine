package com.techlounge.creativeeye;

import com.techlounge.creativeeye.error.CEErrorCallback;
import com.techlounge.creativeeye.error.CEException;
import com.techlounge.creativeeye.error.CEWindowException;
import com.techlounge.creativeeye.error.ErrorCode;
import com.techlounge.creativeeye.graphics.CERenderer;
import com.techlounge.creativeeye.io.CEKeyboardMouseListener;
import com.techlounge.creativeeye.io.CEMouse;
import com.techlounge.creativeeye.io.CEWindow;
import com.techlounge.creativeeye.physics.CEPhysicsEngine;
import org.lwjgl.glfw.GLFW;

public class Engine implements CEKeyboardMouseListener {

    private boolean isEngineRunning = true;

    //Window
    private CEWindow window;

    //Graphics
    private CERenderer CERenderer;

    //Physics
    private CEPhysicsEngine CEPhysicsEngine;

    public Engine() {
        this.init();
    }

    public void init() {
        System.out.println("Game Engine Initialization");
        this.CERenderer = new CERenderer();
        this.CEPhysicsEngine = new CEPhysicsEngine();
        this.window = new CEWindow(1000, 700);
        try {
            this.window.create();
            this.window.setKeyboardMouseListener(this);
        } catch (CEException e) {
            this.onError(e);
        }
    }

    public void start() {
        this.run();
    }

    private void run() {
        while(!this.window.shouldWindowClose()) {
           //Render graphics
            this.CERenderer.render();
            //Update physics
            this.CEPhysicsEngine.update();

            //Updating event on window
            this.window.updateEvents();

            //Window buffer swap
            this.window.render();
        }
    }

    public void stop() {
        this.isEngineRunning = false;
    }

    public void onError(CEException error) {
        System.out.println(error.getMessage());
        System.exit(-1);
    }

    @Override
    public void onKeyboard(int key, int scancode, int action, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            this.window.close();
        }
    }

    @Override
    public void onMouse(CEMouse.Button button, CEMouse.Action action, CEMouse.Mod[] mods, double xPos, double yPos) {
//        System.out.println("MOUSE : Button : " + button + " | Action : " + action + " | X(" + xPos + "), Y(" + yPos + ") | Mods : " + mods[0] + (mods.length == 2 ? ", " + mods[1] : "") + (mods.length == 3 ? ", " + mods[1] + ", " + mods[2]: ""));
    }
}
