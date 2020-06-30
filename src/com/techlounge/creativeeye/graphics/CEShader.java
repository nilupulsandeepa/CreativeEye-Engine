package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.CEEngine;
import com.techlounge.creativeeye.error.CEShaderException;
import com.techlounge.creativeeye.utils.CEFileUtils;
import org.lwjgl.opengl.GL32;

public class CEShader {

    private String filePath;

    private String vertexShader;
    private String fragmentShader;

    private int vertexShaderID;
    private int fragmentShaderID;
    private int shaderProgramID;

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

    public void releaseShaderProgram() {
        GL32.glDeleteProgram(this.shaderProgramID);
    }
}
