package com.official.boot.website.service;

import com.official.boot.common.model.Option;
import com.official.boot.website.model.entity.OfficialWebsiteNav;
import com.official.boot.website.model.form.OfficialWebsiteNavForm;
import com.official.boot.website.model.query.OfficialWebsiteNavQuery;
import com.official.boot.website.model.vo.OfficialWebsiteNavVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.official.boot.website.model.vo.OfficialWebsitePublicNavVO;

import java.util.List;

/**
 * 官网动态导航服务类
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
public interface OfficialWebsiteNavService extends IService<OfficialWebsiteNav> {


    /**
     * 获取官网动态导航表单数据
     *
     * @param id 官网动态导航ID
     * @return 官网动态导航表单数据
     */
     OfficialWebsiteNavForm getOfficialWebsiteNavFormData(Long id);

    /**
     * 新增官网动态导航
     *
     * @param formData 官网动态导航表单对象
     * @return 是否新增成功
     */
    boolean saveOfficialWebsiteNav(OfficialWebsiteNavForm formData);

    /**
     * 修改官网动态导航
     *
     * @param id   官网动态导航ID
     * @param formData 官网动态导航表单对象
     * @return 是否修改成功
     */
    boolean updateOfficialWebsiteNav(Long id, OfficialWebsiteNavForm formData);

    /**
     * 删除官网动态导航
     *
     * @param ids 官网动态导航ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteOfficialWebsiteNavs(String ids);

    /**
     * @description 获取官网导航列表(管理端)
     * @param queryParams 查询参数
     * @return java.util.List<com.official.boot.website.model.vo.OfficialWebsiteNavVO>
     * @author hzl
     * @date 08/12/2025 18:43
     */
    List<OfficialWebsiteNavVO> getAllNavs(OfficialWebsiteNavQuery queryParams);

    /**
     * @description 查询导航菜单下拉数据
     * @param onlyTop 是否只查询顶级菜单
     * @return java.util.List<com.official.boot.common.model.Option<java.lang.Long>>
     * @author hzl
     * @date 08/12/2025 19:19
     */
    List<Option<Long>> getNavOptions(boolean onlyTop);

    /**
     * @description 查询官网导航
     * @return java.util.List<com.official.boot.website.model.vo.OfficialWebsitePublicNavVO>
     * @author hzl
     * @date 09/12/2025 11:07
     */
    List<OfficialWebsitePublicNavVO> getPublicNavs();
}
