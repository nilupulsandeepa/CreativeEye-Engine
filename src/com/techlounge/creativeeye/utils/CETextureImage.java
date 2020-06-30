package com.techlounge.creativeeye.utils;

import java.nio.ByteBuffer;

public class CETextureImage {
    private ByteBuffer textureByteBuffer;
    private int width;
    private int height;

    public CETextureImage(ByteBuffer textureByteBuffer, int width, int height) {
        this.textureByteBuffer = textureByteBuffer;
        this.width = width;
        this.height = height;
    }

    public ByteBuffer getTextureByteBuffer() {
        return textureByteBuffer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
