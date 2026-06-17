package com.official.boot.website.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 官网动态导航视图对象
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
@Getter
@Setter
@Schema( description = "官网动态导航视图对象")
public class OfficialWebsiteNavVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;
    @Schema(description = "父菜单ID")
    private Long parentId;
    @Schema(description = "菜单类型（1 外链 , 2 路由）")
    private Integer type;
    @Schema(description = "跳转地址")
    private String linkTo;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "显示状态（1-显示 0-隐藏）")
    private Integer visible;

    @Schema(description = "创建人 ID")
    private Long createBy;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新人ID")
    private Long updateBy;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


    @Schema(description = "子菜单")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<OfficialWebsiteNavVO> children;
}
