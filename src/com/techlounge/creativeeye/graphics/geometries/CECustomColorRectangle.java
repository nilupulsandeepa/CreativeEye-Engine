package com.techlounge.creativeeye.graphics.geometries;

import com.techlounge.creativeeye.graphics.CEColor;
import com.techlounge.creativeeye.graphics.CEMesh;
import com.techlounge.creativeeye.graphics.CEShader;
import com.techlounge.creativeeye.graphics.CEVertex;
import com.techlounge.creativeeye.maths.CEVector3f;

public class CECustomColorRectangle extends CEMesh {
    public CECustomColorRectangle() {
        this.vertices = new CEVertex[] {
                new CEVertex(new CEVector3f(-0.5F, 0.5F, 0F), new CEColor(54, 214, 64, 255)),
                new CEVertex(new CEVector3f(-0.5F, -0.5F, 0F), new CEColor(209, 52, 192, 255)),
                new CEVertex(new CEVector3f(0.5F, -0.5F, 0F), new CEColor(54, 77, 207, 255)),
                new CEVertex(new CEVector3f(0.5F, 0.5F, 0.0F), new CEColor(207, 204, 56, 255))
        };
        this.indices = new int[] {0, 1, 2, 2, 3, 0};
        this.meshShader = new CEShader("SimpleColorVertexShader.glsl", "SimpleColorFragmentShader.glsl");
        this.create();
    }
}
