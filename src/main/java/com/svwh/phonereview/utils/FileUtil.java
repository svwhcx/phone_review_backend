package com.svwh.phonereview.utils;

import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/22 21:54
 */
public class FileUtil {


    private static String[] imageTypes = new String[]{".jpg",".png",".jpeg",".webp"};

    public static void checkSize(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        double size = (double) bytes.length / 1024 / 1024;
        if (size > 4){
            throw new BusinessException(DefaultErrorCode.IMAGE_TOO_LARGE);
        }
    }

    public static void validImageType(String suffix){
        for (String imageType : imageTypes) {
            if (imageType.equalsIgnoreCase(suffix)){
                return;
            }
        }
        throw new BusinessException(DefaultErrorCode.INVALID_IMAGE_TYPE);
    }


}
