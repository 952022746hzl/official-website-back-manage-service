package com.official.boot.website.converter;

import org.mapstruct.Mapper;
import com.official.boot.website.model.entity.OfficialWebsiteNav;
import com.official.boot.website.model.form.OfficialWebsiteNavForm;
import com.official.boot.website.model.vo.OfficialWebsiteNavVO;
import org.mapstruct.MappingTarget;

/**
 * 官网动态导航对象转换器
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
@Mapper(componentModel = "spring")
public interface OfficialWebsiteNavConverter{

    OfficialWebsiteNavForm toForm(OfficialWebsiteNav entity);

    OfficialWebsiteNav toEntity(OfficialWebsiteNavForm formData);

    OfficialWebsiteNavVO toVo(OfficialWebsiteNav entity);

    void updateEntityFromSaveDto(OfficialWebsiteNavForm formData,@MappingTarget OfficialWebsiteNav dbEntity);
}
