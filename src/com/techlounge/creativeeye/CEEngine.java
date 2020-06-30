package com.techlounge.creativeeye;

import com.techlounge.creativeeye.error.CEErrorCallback;
import com.techlounge.creativeeye.error.CEException;
import com.techlounge.creativeeye.graphics.CERenderer;
import com.techlounge.creativeeye.graphics.CETexture;
import com.techlounge.creativeeye.graphics.geometries.*;
import com.techlounge.creativeeye.io.CEInput;
import com.techlounge.creativeeye.io.CEKeyboardMouseListener;
import com.techlounge.creativeeye.io.CEWindow;
import com.techlounge.creativeeye.io.CEWindowResizeCallback;
import com.techlounge.creativeeye.physics.CEPhysicsEngine;

public class CEEngine implements CEKeyboardMouseListener, CEWindowResizeCallback, CEErrorCallback {

    public static CEEngine errorCallback;

    //Window
    private CEWindow window;

    //Graphics
    private CERenderer renderer;

    //Physics
    private CEPhysicsEngine physicsEngine;

    public CEEngine() {
        this.init();
    }

    public void init() {
        CEEngine.errorCallback = this;
        this.window = new CEWindow(1000, 700);
        this.window.create();
        this.window.setKeyboardMouseListener(this);
        this.window.setWindowSizeCallback(this);
        this.renderer = new CERenderer(this.window.getWindowWidth(), this.window.getWindowHeight());
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

    @Override
    public void onError(CEException error) {
        System.out.println(error.getMessage());
        this.window.close();
        System.exit(-1);
    }

    //Keyboard and mouse callback
    @Override
    public void onKeyboard(CEInput.Key key, int scancode, CEInput.KeyAction action, CEInput.Mod[] mods) {
//        System.out.println("Keyboard : Key : " + key + " | Action : " + action + " | Mods : " + mods[0] + (mods.length == 2 ? ", " + mods[1] : "") + (mods.length == 3 ? ", " + mods[1] + ", " + mods[2]: ""));
        if (key == CEInput.Key.KEY_ESCAPE && action == CEInput.KeyAction.KEY_RELEASE) {
            this.window.close();
        } else if (key == CEInput.Key.KEY_F && mods.length == 1 && mods[0] == CEInput.Mod.CONTROL_MOD && action == CEInput.KeyAction.KEY_RELEASE) {
            this.window.toggleFullScreen();
        } else if (key == CEInput.Key.KEY_Q && mods[0] == CEInput.Mod.NO_MOD && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
            this.renderer.addMesh(new CETriangle());
        } else if (key == CEInput.Key.KEY_W && mods[0] == CEInput.Mod.NO_MOD && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
            this.renderer.addMesh(new CERectangle());
        } else if (key == CEInput.Key.KEY_E && mods[0] == CEInput.Mod.NO_MOD && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
            this.renderer.addMesh(new CEColorTriangle());
        } else if (key == CEInput.Key.KEY_R && mods[0] == CEInput.Mod.NO_MOD && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
            this.renderer.addMesh(new CEColorRectangle());
        } else if (key == CEInput.Key.KEY_T && mods[0] == CEInput.Mod.NO_MOD && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
            this.renderer.addMesh(new CECustomColorTriangle());
        } else if (key == CEInput.Key.KEY_Y && mods[0] == CEInput.Mod.NO_MOD && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
            this.renderer.addMesh(new CECustomColorRectangle());
        } else if (key == CEInput.Key.KEY_U && mods[0] == CEInput.Mod.NO_MOD && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
            this.renderer.addMesh(new CETextureRectangle());
        } else if (key == CEInput.Key.KEY_I && mods[0] == CEInput.Mod.NO_MOD && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
            this.renderer.addMesh(new CETextureRectangle("texture1.png"));
        } else if (key == CEInput.Key.KEY_DELETE && action == CEInput.KeyAction.KEY_PRESS) {
            this.renderer.clearAllMeshes();
        }
    }

    @Override
    public void onMouse(CEInput.MouseButton button, CEInput.MouseButtonAction action, CEInput.Mod[] mods, double xPos, double yPos) {
//        System.out.println("MOUSE : Button : " + button + " | Action : " + action + " | X(" + xPos + "), Y(" + yPos + ") | Mods : " + mods[0] + (mods.length == 2 ? ", " + mods[1] : "") + (mods.length == 3 ? ", " + mods[1] + ", " + mods[2]: ""));
    }

    @Override
    public void onScroll(double xOffset, double yOffset) {
//        System.out.println("XScroll : " + xOffset + " | YScroll : " + yOffset);
    }

    //Window resize callback
    @Override
    public void onResize(int width, int height) {
//        System.out.println("Width : " + width + " | Height : " + height);
        this.renderer.setRenderViewPort(width, height);
    }
}
