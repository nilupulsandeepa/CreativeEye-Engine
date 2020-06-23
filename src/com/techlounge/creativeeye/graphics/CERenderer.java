package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.graphics.models.CEColor;
import org.lwjgl.opengl.*;

public class CERenderer {

    private CEColor clearColor;

    public CERenderer() {
        System.out.println("Renderer Initialization");
        this.init();
    }

    private void init() {
        this.clearColor = new CEColor(1.0F, 1.0F, 0.0F, 1.0F);
    }

    public void setupOpenGL() {
        GL.createCapabilities();
        GL11.glViewport(0, 0, 1000, 500);
    }

    public void render() {
        GL11.glClearColor(this.clearColor.getRed(), this.clearColor.getGreen(), this.clearColor.getBlue(), this.clearColor.getAlpha());
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }
}
