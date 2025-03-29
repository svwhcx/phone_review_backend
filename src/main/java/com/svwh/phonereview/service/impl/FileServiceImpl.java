package com.svwh.phonereview.service.impl;

import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.domain.entity.Photo;
import com.svwh.phonereview.mapper.PhotoMapper;
import com.svwh.phonereview.service.FileService;
import com.svwh.phonereview.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/29 17:55
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final PhotoMapper photoMapper;

    // 文件访问的前缀
    @Value("${application.url.prefix}")
    private String urlPrefix;

    @Value("${application.file.path}")
    private String filePath;

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File dir = new File(filePath);
        // 如果目录不存在则直接创建
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 检查图片后缀是否正常
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        FileUtil.validImageType(suffix);
        String uid = UUID.randomUUID().toString();
        String fileName = uid + suffix;
        File file = new File(filePath, fileName);
        // 将文件存储到磁盘中
        multipartFile.transferTo(Paths.get(file.getPath()));
        // 获取url
        String photoAddr = urlPrefix + fileName;
        Photo photo = new Photo();
        photo.setAddr(photoAddr);
        photo.setUserId(UserInfoThreadLocal.get().getUserId());
        this.photoMapper.insert(photo);
        return photoAddr;
    }
}
