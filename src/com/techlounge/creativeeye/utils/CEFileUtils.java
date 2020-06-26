package com.techlounge.creativeeye.utils;

import com.techlounge.creativeeye.CEEngine;
import com.techlounge.creativeeye.error.CEFileException;
import com.techlounge.creativeeye.error.CEErrorCode;

import java.io.*;
import java.util.Properties;

public class CEFileUtils {
    public static String loadAsString(String filePath) {
        StringBuilder fileString = new StringBuilder();
        try (
            InputStream inputStream = new FileInputStream(filePath);
            InputStreamReader fileStream = new InputStreamReader(inputStream);
            BufferedReader fileReader = new BufferedReader(fileStream)
        ) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                fileString.append(line).append('\n');
            }
        } catch (IOException e) {
            CEEngine.errorCallback.onError(new CEFileException(CEErrorCode.JAVA_CANNOT_READ_SHADER_FILE.errorMessage));
            return null;
        }
        return fileString.toString();
    }

    public static String getProperty(String property) {
        Properties properties = new Properties();
        String propertiesFileName = "paths.properties";
        try (InputStream propertiesStream = CEEngine.class.getResourceAsStream(propertiesFileName)) {
            properties.load(propertiesStream);
        } catch (Exception e) {
            CEEngine.errorCallback.onError(new CEFileException(CEErrorCode.JAVA_CANNOT_READ_PROPERTIES_FILE.errorMessage));
            return null;
        }
        String propertyValue = properties.getProperty(property);
        if (propertyValue == null) {
            CEEngine.errorCallback.onError(new CEFileException(CEErrorCode.JAVA_CANNOT_READ_PROPERTY.errorMessage));
            return null;
        }
        return propertyValue;
    }
}
