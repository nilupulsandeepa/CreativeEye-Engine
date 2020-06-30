package com.techlounge.creativeeye.graphics.geometries;

import com.techlounge.creativeeye.graphics.*;
import com.techlounge.creativeeye.maths.CEVector2f;
import com.techlounge.creativeeye.maths.CEVector3f;

public class CETextureRectangle extends CEMesh {
    public CETextureRectangle() {
        this.vertices = new CEVertex[] {
                new CEVertex(new CEVector3f(-0.5F, 0.5F, 0F), new CEVector2f(0F, 0F)),
                new CEVertex(new CEVector3f(-0.5F, -0.5F, 0F), new CEVector2f(0F, 1F)),
                new CEVertex(new CEVector3f(0.5F, -0.5F, 0F), new CEVector2f(1F, 1F)),
                new CEVertex(new CEVector3f(0.5F, 0.5F, 0.0F), new CEVector2f(1F, 0F))
        };
        this.indices = new int[] {0, 1, 2, 2, 3, 0};
        this.meshShader = new CEShader("SimpleTextureVertexShader.glsl", "SimpleTextureFragmentShader.glsl");
        CETexture texture = new CETexture("texture2.png");
        this.material = new CEMaterial(texture);
        this.create();
    }

    public CETextureRectangle(String textureName) {
        this.vertices = new CEVertex[] {
                new CEVertex(new CEVector3f(-0.5F, 0.5F, 0F), new CEVector2f(0F, 0F)),
                new CEVertex(new CEVector3f(-0.5F, -0.5F, 0F), new CEVector2f(0F, 1F)),
                new CEVertex(new CEVector3f(0.5F, -0.5F, 0F), new CEVector2f(1F, 1F)),
                new CEVertex(new CEVector3f(0.5F, 0.5F, 0.0F), new CEVector2f(1F, 0F))
        };
        this.indices = new int[] {0, 1, 2, 2, 3, 0};
        this.meshShader = new CEShader("SimpleTextureVertexShader.glsl", "SimpleTextureFragmentShader.glsl");
        CETexture texture = new CETexture(textureName);
        this.material = new CEMaterial(texture);
        this.create();
    }
}
