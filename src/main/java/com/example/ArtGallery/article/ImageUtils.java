package com.example.ArtGallery.article;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ImageUtils {
    private static final Map<String, String> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put("jpg", "image/jpeg");
        MIME_TYPES.put("jpeg", "image/jpeg");
        MIME_TYPES.put("png", "image/png");
        // 여기에 필요한 이미지 타입과 MIME 타입을 추가합니다.
    }

    public static String getMimeTypeFromFileName(String fileName) {
        String ext = getExtensionFromFileName(fileName);
        return MIME_TYPES.getOrDefault(ext, "application/octet-stream");
    }

    public static String getExtensionFromFileName(String fileName) {
        Path path = Paths.get(fileName);
        String ext = path.getFileName().toString();
        int dotIndex = ext.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < ext.length() - 1) {
            return ext.substring(dotIndex + 1);
        }
        return "";
    }

}
