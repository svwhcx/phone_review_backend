package com.svwh.phonereview.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章数据传输对象
 * 用于在系统间传递文章信息
 */
@Data
@Accessors(chain = true)
public class PostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 作者头像
     */
    private String authorAvatar;

    /**
     * 文章内容摘要
     */
    private String summary;

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 文章创建时间
     */
    private LocalDateTime createTime;

    /**
     * 查看次数
     */
    private Integer viewCount;

    /**
     * 点赞次数
     */
    private Long likeCount;

    /**
     * 评论次数
     */
    private Integer commentCount;

    /**
     * 收藏次数
     */
    private Integer favoriteCount;

    /**
     * 相关手机型号ID
     */
    private Long phoneModelId;

    /**
     * 相关手机型号名称
     */
    private String phoneModelName;

    /**
     * 推荐分数（用于排序）
     */
    private Double recommendScore;
}