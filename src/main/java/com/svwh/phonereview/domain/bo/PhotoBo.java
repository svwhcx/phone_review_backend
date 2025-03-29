package com.svwh.phonereview.domain.bo;

import com.svwh.phonereview.domain.entity.Photo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/29 17:52
 */
@Data
@AutoMapper(target = Photo.class)
public class PhotoBo {

    private Long id;

    private String addr;

    private Long userId;
}
