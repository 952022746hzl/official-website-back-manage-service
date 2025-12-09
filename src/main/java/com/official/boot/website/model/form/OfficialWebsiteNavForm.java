package com.official.boot.website.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

/**
 * 官网动态导航表单对象
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
@Getter
@Setter
@Schema(description = "官网动态导航表单对象")
public class OfficialWebsiteNavForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "父菜单ID")
    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;

    @Schema(description = "菜单类型（1 外链 , 2 路由）")
    @NotNull(message = "菜单类型（1 外链 , 2 路由）不能为空")
    private Integer type;

    @Schema(description = "跳转地址")
    @Size(max=1024, message="跳转地址长度不能超过1024个字符")
    private String linkTo;

    @Schema(description = "标题")
    @Size(max=64, message="标题长度不能超过64个字符")
    private String title;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "备注")
    @Size(max=255, message="备注长度不能超过255个字符")
    private String remark;
}
