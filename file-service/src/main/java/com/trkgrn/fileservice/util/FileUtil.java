package com.trkgrn.fileservice.util;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {

    public String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String getMimeType(String fileName) {
        String[] parts = fileName.split("\\.");
        return parts[parts.length - 1];
    }
}
