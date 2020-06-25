package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.maths.CEVector3f;

public class CEVertex {
    private CEVector3f position;

    public CEVertex(CEVector3f position) {
        this.position = position;
    }

    public CEVector3f getPosition() {
        return position;
    }
}
