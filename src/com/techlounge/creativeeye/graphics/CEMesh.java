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
    public int uvBuffer;
    public int indexBuffer;

    private ArrayList<String> attributeNameList;

    public CEShader meshShader;

    public CEMaterial material;

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

    public CEMesh(CEVertex[] vertices, int[] indices, CEShader shader, CEMaterial material) {
        this(vertices, indices, shader);
        this.material = material;
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
        GL32.glVertexAttribPointer(this.attributeNameList.size(), 3, GL32.GL_FLOAT, false, 0, 0);
        GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, 0);

        this.attributeNameList.add("vertexPosition");

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
            GL32.glVertexAttribPointer(this.attributeNameList.size(), 4, GL32.GL_FLOAT, false, 0, 0);
            GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, 0);

            this.attributeNameList.add("vertexColor");
        }

        if (this.vertices[0].getUvCoordinates() != null) {
            FloatBuffer uvDataBuffer = MemoryUtil.memAllocFloat(this.vertices.length * 2);
            float[] uvData = new float[this.vertices.length * 2];
            for (int i = 0; i < this.vertices.length; i++) {
                uvData[i * 2] = this.vertices[i].getUvCoordinates().getX();
                uvData[(i * 2) + 1] = this.vertices[i].getUvCoordinates().getY();
            }
            uvDataBuffer.put(uvData).flip();

            this.uvBuffer = GL32.glGenBuffers();
            GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, this.uvBuffer);
            GL32.glBufferData(GL32.GL_ARRAY_BUFFER, uvDataBuffer, GL32.GL_STATIC_DRAW);
            GL32.glVertexAttribPointer(this.attributeNameList.size(), 2, GL32.GL_FLOAT, false, 0, 0);
            GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, 0);

            this.attributeNameList.add("uvCoordinates");
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

        if (this.material != null) {
            this.material.create();
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
        if (this.material != null) {
            GL32.glActiveTexture(GL32.GL_TEXTURE0);
            GL32.glBindTexture(GL32.GL_TEXTURE_2D, this.material.getTexture().getTexture());
        }
        if (this.meshShader != null) {
            GL32.glUseProgram(this.meshShader.getShaderProgram());
            if (this.meshShader.shouldBindUniform()) {
                this.meshShader.bindUniform("scale", 2.0F);
                this.meshShader.bindUniform("tintColor", new CEColor(0.71F, 0.9F, 0.11F, 1.0F));
            }
            GL32.glDrawElements(GL32.GL_TRIANGLES, this.indices.length, GL32.GL_UNSIGNED_INT, 0);
            GL32.glUseProgram(0);
        } else {
            GL32.glDrawElements(GL32.GL_TRIANGLES, this.indices.length, GL32.GL_UNSIGNED_INT, 0);
        }
        if (this.material != null) {
            GL32.glBindTexture(GL32.GL_TEXTURE_2D, 0);
        }
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, 0);
        for (int i = 0; i < this.attributeNameList.size(); i++) {
            GL32.glDisableVertexAttribArray(i);
        }
        GL32.glBindVertexArray(0);
    }

    public void releaseMesh() {
        this.attributeNameList.clear();
        this.vertices = null;
        this.indices = null;
        GL32.glDeleteBuffers(this.vertexBuffer);
        GL32.glDeleteBuffers(this.indexBuffer);
        GL32.glDeleteVertexArrays(this.vertexArray);
        if (this.meshShader != null) {
            this.meshShader.releaseShaderProgram();
        }
        if (this.material != null) {
            this.material.getTexture().release();
        }
    }
}
