package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.CEEngine;
import com.techlounge.creativeeye.error.CEErrorCallback;
import com.techlounge.creativeeye.error.CEErrorCode;
import com.techlounge.creativeeye.error.CEFileException;
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
        this.create();
    }

    public void create() {
        this.shaderProgramID = GL32.glCreateProgram();
        this.vertexShaderID = GL32.glCreateShader(GL32.GL_VERTEX_SHADER);
        GL32.glShaderSource(this.vertexShaderID, this.vertexShader);
        GL32.glCompileShader(this.vertexShaderID);

        if (GL32.glGetShaderi(this.vertexShaderID, GL32.GL_COMPILE_STATUS) == GL32.GL_FALSE) {
            System.out.println("Vertex Shader Problem" + GL32.glGetShaderInfoLog(this.vertexShaderID));
            return;
        }

        this.fragmentShaderID = GL32.glCreateShader(GL32.GL_FRAGMENT_SHADER);
        GL32.glShaderSource(this.fragmentShaderID, this.fragmentShader);
        GL32.glCompileShader(this.fragmentShaderID);

        if (GL32.glGetShaderi(this.fragmentShaderID, GL32.GL_COMPILE_STATUS) == GL32.GL_FALSE) {
            System.out.println("Fragment Shader Problem" + GL32.glGetShaderInfoLog(this.fragmentShaderID));
            return;
        }

        GL32.glAttachShader(this.shaderProgramID, this.vertexShaderID);
        GL32.glAttachShader(this.shaderProgramID, this.fragmentShaderID);

        GL32.glLinkProgram(this.shaderProgramID);
        if (GL32.glGetProgrami(this.shaderProgramID, GL32.GL_LINK_STATUS) == GL32.GL_FALSE) {
            System.out.println("Program Link Problem");
            return;
        }

        GL32.glValidateProgram(this.shaderProgramID);
        if (GL32.glGetProgrami(this.shaderProgramID, GL32.GL_VALIDATE_STATUS) == GL32.GL_FALSE) {
            System.out.println("Program Validate Problem");
            return;
        }

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
