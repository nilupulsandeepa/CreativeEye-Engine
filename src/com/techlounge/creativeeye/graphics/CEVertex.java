package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.maths.CEVector3f;

public class CEVertex {
    private CEVector3f position;
    private CEColor color;

    public CEVertex(CEVector3f position) {
        this.position = position;
    }

    public CEVertex(CEVector3f position, CEColor color) {
        this(position);
        this.color = color;
    }

    public CEVector3f getPosition() {
        return position;
    }

    public CEColor getColor() {
        return color;
    }
}
