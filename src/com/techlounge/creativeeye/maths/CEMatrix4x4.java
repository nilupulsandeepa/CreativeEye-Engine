package com.techlounge.creativeeye.maths;

public class CEMatrix4x4 {
    public static final short MATRIX_SIZE = 4;
    private float[] elements = new float[MATRIX_SIZE * MATRIX_SIZE];

    public float getElement(short x, short y) {
        return elements[y * MATRIX_SIZE + x];
    }

    public void setElement(short x, short y, float value) {
        this.elements[y + MATRIX_SIZE + x] = value;
    }

    public float[] getElements() {
        return elements;
    }
}
