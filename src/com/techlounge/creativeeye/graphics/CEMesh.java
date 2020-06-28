package com.techlounge.creativeeye.graphics;

import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class CEMesh {
    public CEVertex[] vertices;
    public int[] indices;

    public int vertexArray;

    public int vertexBuffer;
    public int colorBuffer;
    public int indexBuffer;

    private ArrayList<String> attributeNameList;

    public CEShader meshShader;

    public CEMesh() {

    }

    public CEMesh(CEVertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
        this.create();
    }

    public CEMesh(CEVertex[] vertices, int[] indices, CEShader shader) {
        this(vertices, indices);
        this.meshShader = shader;
    }

    public void create() {
        this.attributeNameList = new ArrayList<>();
        //Create a Vertex Array Object which hold multiple buffers inside on object (VertexBuffer, TextureBuffer, Normal Buffer)
        this.vertexArray = GL32.glGenVertexArrays();
        //Bind Vertex Array Object
        GL32.glBindVertexArray(this.vertexArray);

        FloatBuffer vertexDataBuffer = MemoryUtil.memAllocFloat(this.vertices.length * 3);
        float[] vertexData = new float[this.vertices.length * 3];
        for (int i = 0; i < this.vertices.length; i++) {
            vertexData[i * 3] = this.vertices[i].getPosition().getX();
            vertexData[(i * 3) + 1] = this.vertices[i].getPosition().getY();
            vertexData[(i * 3) + 2] = this.vertices[i].getPosition().getZ();
        }
        vertexDataBuffer.put(vertexData).flip();

        //Generate buffer for vbo
        this.vertexBuffer = GL32.glGenBuffers();
        GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, this.vertexBuffer);
        GL32.glBufferData(GL32.GL_ARRAY_BUFFER, vertexDataBuffer, GL32.GL_STATIC_DRAW);
        GL32.glVertexAttribPointer(0, 3, GL32.GL_FLOAT, false, 0, 0);
        GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, 0);

        attributeNameList.add("vertexPosition");

        if (this.vertices[0].getColor() != null) {
            FloatBuffer colorDataBuffer = MemoryUtil.memAllocFloat(this.vertices.length * 4);
            float[] colorData = new float[this.vertices.length * 4];
            for (int i = 0; i < this.vertices.length; i++) {
                colorData[i * 4] = this.vertices[i].getColor().getRed();
                colorData[(i * 4) + 1] = this.vertices[i].getColor().getGreen();
                colorData[(i * 4) + 2] = this.vertices[i].getColor().getBlue();
                colorData[(i * 4) + 3] = this.vertices[i].getColor().getAlpha();
            }
            colorDataBuffer.put(colorData).flip();

            this.colorBuffer = GL32.glGenBuffers();
            GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, this.colorBuffer);
            GL32.glBufferData(GL32.GL_ARRAY_BUFFER, colorDataBuffer, GL32.GL_STATIC_DRAW);
            GL32.glVertexAttribPointer(1, 4, GL32.GL_FLOAT, false, 0, 0);
            GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, 0);

            attributeNameList.add("vertexColor");
        }

        GL32.glBindVertexArray(0);

        IntBuffer indexDataBuffer = MemoryUtil.memAllocInt(this.indices.length);
        indexDataBuffer.put(this.indices).flip();

        //Generate buffer for ibo
        this.indexBuffer = GL32.glGenBuffers();
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, this.indexBuffer);
        GL32.glBufferData(GL32.GL_ELEMENT_ARRAY_BUFFER, indexDataBuffer, GL32.GL_STATIC_DRAW);
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, 0);

        if (this.meshShader != null) {
            this.meshShader.create(attributeNameList.toArray(new String[0]));
        }
    }

    public int getVertexArray() {
        return vertexArray;
    }

    public int getVertexBuffer() {
        return vertexBuffer;
    }

    public int getIndexBuffer() {
        return indexBuffer;
    }

    public void render() {
        GL32.glBindVertexArray(this.vertexArray);
        for (int i = 0; i < this.attributeNameList.size(); i++) {
            GL32.glEnableVertexAttribArray(i);
        }
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, this.indexBuffer);
        if (this.meshShader != null) {
            GL32.glUseProgram(this.meshShader.getShaderProgram());
            GL32.glDrawElements(GL32.GL_TRIANGLES, this.indices.length, GL32.GL_UNSIGNED_INT, 0);
            GL32.glUseProgram(0);
        } else {
            GL32.glDrawElements(GL32.GL_TRIANGLES, this.indices.length, GL32.GL_UNSIGNED_INT, 0);
        }
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, 0);
        for (int i = 0; i < this.attributeNameList.size(); i++) {
            GL32.glDisableVertexAttribArray(i);
        }
        GL32.glBindVertexArray(0);
    }

    public void releaseMesh() {
        GL32.glDeleteBuffers(this.vertexBuffer);
        GL32.glDeleteBuffers(this.indexBuffer);
        GL32.glDeleteVertexArrays(this.vertexArray);
        if (this.meshShader != null) {
            this.meshShader.releaseShaderProgram();
        }
    }
}
