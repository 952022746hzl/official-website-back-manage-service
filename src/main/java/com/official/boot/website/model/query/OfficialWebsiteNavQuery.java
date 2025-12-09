package com.official.boot.website.model.query;

import com.official.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 官网动态导航分页查询对象
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
@Schema(description ="官网动态导航查询对象")
@Getter
@Setter
public class OfficialWebsiteNavQuery {

    @Schema(description = "标题")
    private String title;
}
