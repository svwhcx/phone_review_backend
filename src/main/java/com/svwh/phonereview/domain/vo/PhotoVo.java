package com.svwh.phonereview.domain.vo;

import com.svwh.phonereview.domain.entity.Photo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/29 17:56
 */
@Data
@AutoMapper(target = Photo.class)
public class PhotoVo {

    private Long id;

    private String addr;

    private Long userId;
}
