package com.svwh.phonereview.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/29 17:55
 */
public interface FileService {

    /**
     * 用户上传图片文件
     * @param multipartFile 图片文件数据
     */
    String uploadImage(MultipartFile multipartFile) throws IOException;

}
