package com.techlounge.creativeeye;

import com.techlounge.creativeeye.error.CEException;
import com.techlounge.creativeeye.graphics.CERenderer;
import com.techlounge.creativeeye.io.*;
import com.techlounge.creativeeye.physics.CEPhysicsEngine;

public class Engine implements CEKeyboardMouseListener {

    //Window
    private CEWindow window;

    //Graphics
    private CERenderer renderer;

    //Physics
    private CEPhysicsEngine physicsEngine;

    public Engine() {
        this.init();
    }

    public void init() {
        System.out.println("Game Engine Initialization");
        this.window = new CEWindow(1000, 700);
        try {
            this.window.create();
            this.window.setKeyboardMouseListener(this);
        } catch (CEException e) {
            this.onError(e);
        }
        this.renderer = new CERenderer();
        this.renderer.setupOpenGL();
        this.physicsEngine = new CEPhysicsEngine();
    }

    public void start() {
        this.run();
    }

    private void run() {
        while(!this.window.shouldWindowClose()) {
           //Render graphics
            this.renderer.render();
            //Update physics
            this.physicsEngine.update();

            //Updating event on window
            this.window.updateEvents();

            //Window buffer swap
            this.window.render();
        }
    }

    public void onError(CEException error) {
        System.out.println(error.getMessage());
        System.exit(-1);
    }

    @Override
    public void onKeyboard(CEInput.Key key, int scancode, CEInput.KeyAction action, CEInput.Mod[] mods) {
        System.out.println("Keyboard : Key : " + key + " | Action : " + action + " | Mods : " + mods[0] + (mods.length == 2 ? ", " + mods[1] : "") + (mods.length == 3 ? ", " + mods[1] + ", " + mods[2]: ""));
        if (key == CEInput.Key.KEY_ESCAPE) {
            this.window.close();
        }
    }

    @Override
    public void onMouse(CEInput.MouseButton button, CEInput.MouseButtonAction action, CEInput.Mod[] mods, double xPos, double yPos) {
        System.out.println("MOUSE : Button : " + button + " | Action : " + action + " | X(" + xPos + "), Y(" + yPos + ") | Mods : " + mods[0] + (mods.length == 2 ? ", " + mods[1] : "") + (mods.length == 3 ? ", " + mods[1] + ", " + mods[2]: ""));
    }

    @Override
    public void onScroll(double xOffset, double yOffset) {
        System.out.println("XScroll : " + xOffset + " | YScroll : " + yOffset);
    }
}
