package com.svwh.phonereview.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 点赞管理
 * @Author cxk
 * @Date 2025/3/24 20:51
 */
@Data
public class FavoriteVo  {



    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 目标主键id
     */
    private Long targetId;

    /**
     * 点赞的时间
     */
    private LocalDateTime createTime;

    /**
     * 点赞的类型
     */
    private Integer type;

}
