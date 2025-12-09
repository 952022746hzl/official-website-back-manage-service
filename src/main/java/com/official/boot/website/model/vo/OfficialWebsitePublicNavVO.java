package com.official.boot.website.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
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
public class OfficialWebsitePublicNavVO implements Serializable {
    @Schema(description = "标题")
    private String label;

    @Schema(description = "跳转链接")
    private String to;

    @Schema(description = "在哪里显示链接的URL")
    private String target;

    @Schema(description = "子菜单")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<OfficialWebsitePublicNavVO> children;
}
