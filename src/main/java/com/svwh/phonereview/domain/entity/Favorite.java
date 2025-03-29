package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.FavoriteVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description 点赞管理
 * @Author cxk
 * @Date 2025/3/24 20:51
 */
@Data
@TableName("favorite")
@AutoMapper(target = FavoriteVo.class)
public class Favorite {

    @TableId
    private Long id;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 评测文章主键
     */
    private Long targetId;

    /**
     * 点赞的时间
     */
    private LocalDateTime createTime;

    /**
     * 类型:(1:点赞帖子 2: 收藏帖子；3：点赞评论）
     */
    private Integer type;


}
