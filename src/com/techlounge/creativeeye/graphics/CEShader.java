package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.CEEngine;
import com.techlounge.creativeeye.error.CEShaderException;
import com.techlounge.creativeeye.maths.CEMatrix4x4;
import com.techlounge.creativeeye.maths.CEVector2f;
import com.techlounge.creativeeye.maths.CEVector3f;
import com.techlounge.creativeeye.utils.CEFileUtils;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class CEShader {

    private String filePath;

    private String vertexShader;
    private String fragmentShader;

    private int vertexShaderID;
    private int fragmentShaderID;
    private int shaderProgramID;

    private boolean shouldBindUniform;

    public CEShader(String vertexShaderPath, String fragmentShaderPath) {
        this.filePath = CEFileUtils.getProperty("shaderPath");
        this.vertexShader = CEFileUtils.loadAsString(filePath + vertexShaderPath);
        this.fragmentShader = CEFileUtils.loadAsString(filePath + fragmentShaderPath);
    }

    public void create(String[] attributeNameList) {
        this.shaderProgramID = GL32.glCreateProgram();
        this.vertexShaderID = GL32.glCreateShader(GL32.GL_VERTEX_SHADER);
        GL32.glShaderSource(this.vertexShaderID, this.vertexShader);
        GL32.glCompileShader(this.vertexShaderID);

        if (GL32.glGetShaderi(this.vertexShaderID, GL32.GL_COMPILE_STATUS) == GL32.GL_FALSE) {
            CEEngine.errorCallback.onError(new CEShaderException(GL32.glGetShaderInfoLog(this.vertexShaderID)));
            return;
        }

        this.fragmentShaderID = GL32.glCreateShader(GL32.GL_FRAGMENT_SHADER);
        GL32.glShaderSource(this.fragmentShaderID, this.fragmentShader);
        GL32.glCompileShader(this.fragmentShaderID);

        if (GL32.glGetShaderi(this.fragmentShaderID, GL32.GL_COMPILE_STATUS) == GL32.GL_FALSE) {
            CEEngine.errorCallback.onError(new CEShaderException(GL32.glGetShaderInfoLog(this.fragmentShaderID)));
            return;
        }

        GL32.glAttachShader(this.shaderProgramID, this.vertexShaderID);
        GL32.glAttachShader(this.shaderProgramID, this.fragmentShaderID);

        for (int i = 0; i < attributeNameList.length; i++) {
            GL32.glBindAttribLocation(this.shaderProgramID, i, attributeNameList[i]);
        }

        GL32.glLinkProgram(this.shaderProgramID);
        if (GL32.glGetProgrami(this.shaderProgramID, GL32.GL_LINK_STATUS) == GL32.GL_FALSE) {
            CEEngine.errorCallback.onError(new CEShaderException(GL32.glGetProgramInfoLog(this.shaderProgramID)));
            return;
        }

        GL32.glValidateProgram(this.shaderProgramID);
        if (GL32.glGetProgrami(this.shaderProgramID, GL32.GL_VALIDATE_STATUS) == GL32.GL_FALSE) {
            CEEngine.errorCallback.onError(new CEShaderException(GL32.glGetProgramInfoLog(this.shaderProgramID)));
            return;
        }

        GL32.glDetachShader(this.shaderProgramID, this.vertexShaderID);
        GL32.glDetachShader(this.shaderProgramID, this.fragmentShaderID);

        GL32.glDeleteShader(this.vertexShaderID);
        GL32.glDeleteShader(this.fragmentShaderID);
    }

    public int getShaderProgram() {
        return shaderProgramID;
    }

    public void bindUniform(String name, int value) {
        GL32.glUniform1i(this.getUniformLocation(name), value);
    }

    public void bindUniform(String name, float value) {
        GL32.glUniform1f(this.getUniformLocation(name), value);
    }

    public void bindUniform(String name, boolean value) {
        GL32.glUniform1i(this.getUniformLocation(name), value ? 1 : 0);
    }

    public void bindUniform(String name, CEVector2f value) {
        GL32.glUniform2f(this.getUniformLocation(name), value.getX(), value.getY());
    }

    public void bindUniform(String name, CEVector3f value) {
        GL32.glUniform3f(this.getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }

    public void bindUniform(String name, CEColor value) {
        GL32.glUniform4f(this.getUniformLocation(name), value.getRed(), value.getGreen(), value.getBlue(), value.getAlpha());
    }

    public void bindUniform(String name, CEMatrix4x4 value) {
        FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(CEMatrix4x4.MATRIX_SIZE * CEMatrix4x4.MATRIX_SIZE);
        matrixBuffer.put(value.getElements()).flip();
        GL32.glUniformMatrix4fv(this.getUniformLocation(name), true, matrixBuffer);
    }

    public int getUniformLocation(String uniformName) {
        return GL32.glGetUniformLocation(this.shaderProgramID, uniformName);
    }

    public void enableUniforms() {
        this.shouldBindUniform = true;
    }

    public boolean shouldBindUniform() {
        return this.shouldBindUniform;
    }

    public void releaseShaderProgram() {
        GL32.glDeleteProgram(this.shaderProgramID);
    }
}
