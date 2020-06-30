package com.techlounge.creativeeye.graphics;

import com.techlounge.creativeeye.CEEngine;
import com.techlounge.creativeeye.error.CEErrorCode;
import com.techlounge.creativeeye.error.CETextureException;
import com.techlounge.creativeeye.utils.CEFileUtils;
import com.techlounge.creativeeye.utils.CETextureImage;
import org.lwjgl.opengl.GL32;
import org.lwjgl.stb.STBImage;

public class CETexture {

    private int textureID;

    private CETextureImage textureImage;

    public CETexture(String fileName) {
        String texturePath = CEFileUtils.getProperty("texturePath");
        this.textureImage = CEFileUtils.getTextureImage(texturePath + fileName);
        if (textureImage == null || textureImage.getTextureByteBuffer() == null) {
            CEEngine.errorCallback.onError(new CETextureException(CEErrorCode.TEXTURE_BUFFER_NOT_AVAILABLE.errorMessage));
        }
    }

    public void create() {
        this.textureID = GL32.glGenTextures();
        GL32.glBindTexture(GL32.GL_TEXTURE_2D, this.textureID);
        GL32.glPixelStorei(GL32.GL_UNPACK_ALIGNMENT, 1);
        GL32.glTexImage2D(GL32.GL_TEXTURE_2D, 0, GL32.GL_RGBA, this.textureImage.getWidth(), this.textureImage.getHeight(), 0, GL32.GL_RGBA, GL32.GL_UNSIGNED_BYTE, this.textureImage.getTextureByteBuffer());
        GL32.glGenerateMipmap(GL32.GL_TEXTURE_2D);
        STBImage.stbi_image_free(this.textureImage.getTextureByteBuffer());
        this.textureImage = null;
    }

    public int getTexture() {
        return textureID;
    }

    public void release() {
        GL32.glDeleteTextures(this.textureID);
    }
}
