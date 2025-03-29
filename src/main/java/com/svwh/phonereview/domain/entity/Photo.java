package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.PhotoVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/29 17:56
 */
@Data
@TableName("photo")
@AutoMapper(target = PhotoVo.class)
public class Photo {

    @TableId
    private Long id;

    private String addr;

    private Long userId;
}
