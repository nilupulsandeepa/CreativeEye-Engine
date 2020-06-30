package com.techlounge.creativeeye.graphics;

public class CEMaterial {
    private CETexture texture;

    public CEMaterial(CETexture texture) {
        this.texture = texture;
    }

    public void create() {
        this.texture.create();
    }

    public CETexture getTexture() {
        return texture;
    }
}
