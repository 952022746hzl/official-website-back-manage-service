package com.official.boot.website.model.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.official.boot.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 官网动态导航实体对象
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
@Getter
@Setter
@TableName("official_website_nav")
public class OfficialWebsiteNav extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 菜单类型（1 外链 , 2 路由）
     */
    private Integer type;
    /**
     * 跳转地址
     */
    private String linkTo;
    /**
     * 标题
     */
    private String title;
    /**
     * 备注
     */
    private String remark;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 显示状态（1-显示 0-隐藏）
     */
    private Integer visible;
    /**
     * 创建人 ID
     */
    private Long createBy;
    /**
     * 更新人ID
     */
    private Long updateBy;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;
}
