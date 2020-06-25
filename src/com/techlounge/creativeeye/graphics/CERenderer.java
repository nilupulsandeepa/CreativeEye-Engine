package com.techlounge.creativeeye.graphics;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32;

import java.util.ArrayList;

public class CERenderer {

    private int viewPortWidth;
    private int viewPortHeight;

    private CEColor clearColor;

    private ArrayList<CEMesh> meshArray;

    public CERenderer(int width, int height) {
        this.init(width, height);
    }

    private void init(int width, int height) {
        this.viewPortWidth = width;
        this.viewPortHeight = height;
        this.clearColor = new CEColor(1.0F, 0.42F, 0.0F, 0.38F);
        this.meshArray = new ArrayList<>();
        this.setupOpenGL();
    }

    public void setupOpenGL() {
        GL.createCapabilities();
    }

    public void render() {
        GL32.glViewport(0, 0, this.viewPortWidth, this.viewPortHeight);
        GL32.glClearColor(this.clearColor.getRed(), this.clearColor.getGreen(), this.clearColor.getBlue(), this.clearColor.getAlpha());
        GL32.glClear(GL32.GL_COLOR_BUFFER_BIT);

        for (CEMesh mesh : this.meshArray) {
            mesh.render();
        }
    }

    public void setRenderViewPort(int width, int height) {
        this.viewPortWidth = width;
        this.viewPortHeight = height;
    }

    public void addMesh(CEMesh mesh) {
        this.meshArray.add(mesh);
    }

    public void clearAllMeshes() {
        this.meshArray.clear();
    }
}
