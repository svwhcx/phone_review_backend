package com.svwh.phonereview.controller;

import com.svwh.phonereview.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/29 17:52
 */
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 用户上传一个图片文件
     * @param multipartFile 对应的图片文件
     */
    @PostMapping("/images")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return fileService.uploadImage(multipartFile);
    }
}
