package com.svwh.phonereview.domain.bo;

import com.svwh.phonereview.common.constant.FavoriteConstant;
import com.svwh.phonereview.common.validation.AddGroup;
import com.svwh.phonereview.common.validation.EditGroup;
import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.exception.IErrorCode;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description 点赞管理
 * @Author cxk
 * @Date 2025/3/24 20:51
 */
@Data
@AutoMapper(target = Favorite.class)
public class FavoriteBo {

    private Long id;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 评测文章主键
     */
    @NotNull(groups = {AddGroup.class, EditGroup.class})
    private Long targetId;

    /**
     * 点赞的时间
     */
    private LocalDateTime createTime;

    /**
     * 点赞、收藏类型
     */
    private Integer type;

    /**
     * 提示信息
     */
    private String message;

    private IErrorCode iErrorCode;


    public void buildLikePost(){
        this.type = FavoriteConstant.LIKE_POST;
        this.iErrorCode = DefaultErrorCode.EXIST_LIKE_POST;
    }

    public void buildFavoritePost(){
        this.type = FavoriteConstant.FAVORITE_POST;
        this.iErrorCode = DefaultErrorCode.EXIST_FAVORITE_POST;
    }

    public void buildLikeComment(){
        this.type = FavoriteConstant.LIKE_COMMENT;
        this.iErrorCode = DefaultErrorCode.EXIST_LIKE_COMMENT;
    }
}
