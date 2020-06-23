package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.graphics.models.CEColor;
import org.lwjgl.opengl.*;

public class CERenderer {

    private int viewPortWidth;
    private int viewPortHeight;

    private CEColor clearColor;

    public CERenderer(int width, int height) {
        System.out.println("Renderer Initialization");
        this.init(width, height);
    }

    private void init(int width, int height) {
        this.viewPortWidth = width;
        this.viewPortHeight = height;
        this.clearColor = new CEColor(1.0F, 1.0F, 0.0F, 1.0F);
        this.setupOpenGL();
    }

    public void setupOpenGL() {
        GL.createCapabilities();
    }

    public void render() {
        GL11.glViewport(0, 0, this.viewPortWidth, this.viewPortHeight);
        GL11.glClearColor(this.clearColor.getRed(), this.clearColor.getGreen(), this.clearColor.getBlue(), this.clearColor.getAlpha());
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void setRenderViewPort(int width, int height) {
        this.viewPortWidth = width;
        this.viewPortHeight = height;
    }
}
