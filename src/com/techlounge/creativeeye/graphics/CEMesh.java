package com.techlounge.creativeeye.graphics;

import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class CEMesh {
    public CEVertex[] vertices;
    public int[] indices;

    public int vertexArray;

    public int vertexBuffer;
    public int indexBuffer;

    public CEMesh() {

    }

    public CEMesh(CEVertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
        this.create();
    }

    public void create() {
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

        //Generate buffer for vao
        this.vertexBuffer = GL32.glGenBuffers();
        GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, this.vertexBuffer);
        GL32.glBufferData(GL32.GL_ARRAY_BUFFER, vertexDataBuffer, GL32.GL_STATIC_DRAW);
        GL32.glVertexAttribPointer(0, 3, GL32.GL_FLOAT, false, 0, 0);
        GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, 0);

        IntBuffer indexDataBuffer = MemoryUtil.memAllocInt(this.indices.length);
        indexDataBuffer.put(this.indices).flip();

        this.indexBuffer = GL32.glGenBuffers();
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, this.indexBuffer);
        GL32.glBufferData(GL32.GL_ELEMENT_ARRAY_BUFFER, indexDataBuffer, GL32.GL_STATIC_DRAW);
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, 0);
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
        GL32.glEnableVertexAttribArray(0);
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, this.indexBuffer);
        GL32.glDrawElements(GL32.GL_TRIANGLES, this.indices.length, GL32.GL_UNSIGNED_INT, 0);
        GL32.glBindBuffer(GL32.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL32.glDisableVertexAttribArray(0);
        GL32.glBindVertexArray(0);
    }
}