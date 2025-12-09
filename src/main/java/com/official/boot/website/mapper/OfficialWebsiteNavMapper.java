package com.official.boot.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.official.boot.website.model.entity.OfficialWebsiteNav;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.official.boot.website.model.query.OfficialWebsiteNavQuery;
import com.official.boot.website.model.vo.OfficialWebsiteNavVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 官网动态导航Mapper接口
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
@Mapper
public interface OfficialWebsiteNavMapper extends BaseMapper<OfficialWebsiteNav> {

    /**
     * 获取官网动态导航分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<OfficialWebsiteNavVO>} 官网动态导航分页列表
     */
    Page<OfficialWebsiteNavVO> getOfficialWebsiteNavPage(Page<OfficialWebsiteNavVO> page, OfficialWebsiteNavQuery queryParams);

}
