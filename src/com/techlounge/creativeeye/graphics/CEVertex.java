package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.maths.CEVector2f;
import com.techlounge.creativeeye.maths.CEVector3f;

public class CEVertex {
    private CEVector3f position;
    private CEColor color;
    private CEVector2f uvCoordinates;

    public CEVertex(CEVector3f position) {
        this.position = position;
    }

    public CEVertex(CEVector3f position, CEColor color) {
        this(position);
        this.color = color;
    }

    public CEVertex(CEVector3f position, CEVector2f uvCoordinates) {
        this(position);
        this.uvCoordinates = uvCoordinates;
    }

    public CEVector3f getPosition() {
        return position;
    }

    public CEColor getColor() {
        return color;
    }

    public CEVector2f getUvCoordinates() {
        return uvCoordinates;
    }
}
