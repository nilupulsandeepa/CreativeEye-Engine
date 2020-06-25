package com.techlounge.creativeeye.graphics.geometries;

import com.techlounge.creativeeye.graphics.CEMesh;
import com.techlounge.creativeeye.graphics.CEVertex;
import com.techlounge.creativeeye.maths.CEVector3f;

public class CERectangle extends CEMesh {
    public CERectangle() {
        this.vertices = new CEVertex[] {
                new CEVertex(new CEVector3f(-0.5F, 0.5F, 0F)),
                new CEVertex(new CEVector3f(-0.5F, -0.5F, 0F)),
                new CEVertex(new CEVector3f(0.5F, -0.5F, 0F)),
                new CEVertex(new CEVector3f(0.5F, 0.5F, 0.0F))
        };
        this.indices = new int[] {0, 1, 2, 2, 3, 0};
        this.create();
    }
}
