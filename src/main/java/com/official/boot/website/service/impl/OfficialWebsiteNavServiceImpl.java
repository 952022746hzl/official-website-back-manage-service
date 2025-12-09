package com.official.boot.website.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.official.boot.common.constant.SystemConstants;
import com.official.boot.common.model.Option;
import com.official.boot.core.exception.BusinessException;
import com.official.boot.core.web.ResultCode;
import com.official.boot.website.model.vo.OfficialWebsitePublicNavVO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.official.boot.website.mapper.OfficialWebsiteNavMapper;
import com.official.boot.website.service.OfficialWebsiteNavService;
import com.official.boot.website.model.entity.OfficialWebsiteNav;
import com.official.boot.website.model.form.OfficialWebsiteNavForm;
import com.official.boot.website.model.query.OfficialWebsiteNavQuery;
import com.official.boot.website.model.vo.OfficialWebsiteNavVO;
import com.official.boot.website.converter.OfficialWebsiteNavConverter;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 官网动态导航服务实现类
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
@Service
public class OfficialWebsiteNavServiceImpl extends ServiceImpl<OfficialWebsiteNavMapper, OfficialWebsiteNav> implements OfficialWebsiteNavService {

    @Resource
    private OfficialWebsiteNavConverter officialWebsiteNavConverter;

    /**
     * 获取官网动态导航表单数据
     *
     * @param id 官网动态导航ID
     * @return 官网动态导航表单数据
     */
    @Override
    public OfficialWebsiteNavForm getOfficialWebsiteNavFormData(Long id) {
        OfficialWebsiteNav entity = this.getById(id);
        return officialWebsiteNavConverter.toForm(entity);
    }

    /**
     * 新增官网动态导航
     *
     * @param formData 官网动态导航表单对象
     * @return 是否新增成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOfficialWebsiteNav(OfficialWebsiteNavForm formData) {
        OfficialWebsiteNav entity = officialWebsiteNavConverter.toEntity(formData);
        return this.save(entity);
    }

    /**
     * 更新官网动态导航
     *
     * @param id   官网动态导航ID
     * @param formData 官网动态导航表单对象
     * @return 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOfficialWebsiteNav(Long id,OfficialWebsiteNavForm formData) {
        OfficialWebsiteNav dbEntity = this.getById(id);
        if (Objects.isNull(dbEntity)) {
            throw new BusinessException(ResultCode.RESOURCE_NOT_FOUND);
        }
        officialWebsiteNavConverter.updateEntityFromSaveDto(formData,dbEntity);
        return this.updateById(dbEntity);
    }

    /**
     * 删除官网动态导航
     *
     * @param ids 官网动态导航ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOfficialWebsiteNavs(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的官网动态导航数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    @Override
    public List<OfficialWebsiteNavVO> getAllNavs(OfficialWebsiteNavQuery queryParams) {
        List<OfficialWebsiteNav> officialWebsiteNavs = this.list(new LambdaQueryWrapper<OfficialWebsiteNav>()
                .like(StringUtils.isNotBlank(queryParams.getTitle()), OfficialWebsiteNav::getTitle, queryParams.getTitle())
                .orderByAsc(OfficialWebsiteNav::getSort)
        );

        // 获取所有导航ID
        Set<Long> navIds = officialWebsiteNavs.stream()
                .map(OfficialWebsiteNav::getId)
                .collect(Collectors.toSet());

        // 获取根节点ID（递归的起点），即父级ID为null或0的节点，或者父级ID不在当前导航ID集合中的节点
        List<Long> rootIds = officialWebsiteNavs.stream()
                .filter(nav -> nav.getParentId() == null || nav.getParentId() == 0 || !navIds.contains(nav.getParentId()))
                .map(OfficialWebsiteNav::getId)
                .toList();

        // 使用递归函数来构建导航树
        return rootIds.stream()
                .flatMap(rootId -> buildNavTree(rootId, officialWebsiteNavs).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Option<Long>> getNavOptions(boolean onlyTop) {
        LambdaQueryWrapper<OfficialWebsiteNav> queryWrapper = new LambdaQueryWrapper<>();

        if (onlyTop) {
            queryWrapper.isNull(OfficialWebsiteNav::getParentId)
                    .or()
                    .eq(OfficialWebsiteNav::getParentId, SystemConstants.ROOT_NODE_ID);
        }
        queryWrapper.orderByAsc(OfficialWebsiteNav::getSort);

        List<OfficialWebsiteNav> officialWebsiteNavs = this.list(queryWrapper);

        return buildNavOptions(SystemConstants.ROOT_NODE_ID, officialWebsiteNavs);
    }

    @Override
    public List<OfficialWebsitePublicNavVO> getPublicNavs() {
        // 查询所有未删除的导航，按排序字段升序
        List<OfficialWebsiteNav> navList = this.list(new LambdaQueryWrapper<OfficialWebsiteNav>()
                .orderByAsc(OfficialWebsiteNav::getSort)
        );
        
        // 构建并返回导航树，从根节点开始
        return buildPublicNavTree(SystemConstants.ROOT_NODE_ID, navList);
    }


    private List<Option<Long>> buildNavOptions(Long parentId, List<OfficialWebsiteNav> navList) {
        List<Option<Long>> navOptions = new ArrayList<>();

        for (OfficialWebsiteNav nav : navList) {
            // 处理根节点：parentId为null、0或者等于传入的parentId
            boolean isMatch = false;
            if (parentId.equals(SystemConstants.ROOT_NODE_ID)) {
                // 根节点匹配：parentId为null或0
                isMatch = nav.getParentId() == null || nav.getParentId().equals(SystemConstants.ROOT_NODE_ID);
            } else {
                // 子节点匹配：parentId等于传入的parentId
                isMatch = nav.getParentId() != null && nav.getParentId().equals(parentId);
            }

            if (isMatch) {
                Option<Long> option = new Option<>(nav.getId(), nav.getTitle());
                List<Option<Long>> subNavOptions = buildNavOptions(nav.getId(), navList);
                if (!subNavOptions.isEmpty()) {
                    option.setChildren(subNavOptions);
                }
                navOptions.add(option);
            }
        }

        return navOptions;
    }



    private List<OfficialWebsiteNavVO> buildNavTree(Long navId, List<OfficialWebsiteNav> navList) {
        // 查找当前节点
        return CollectionUtil.emptyIfNull(navList)
                .stream()
                .filter(nav -> nav.getId().equals(navId))
                .map(entity -> {
                    OfficialWebsiteNavVO navVO = officialWebsiteNavConverter.toVo(entity);
                    // 递归查找子节点
                    List<OfficialWebsiteNavVO> children = CollectionUtil.emptyIfNull(navList)
                            .stream()
                            .filter(nav -> nav.getParentId() != null && nav.getParentId().equals(entity.getId()))
                            .flatMap(childNav -> buildNavTree(childNav.getId(), navList).stream())
                            .collect(Collectors.toList());
                    if (!children.isEmpty()) {
                        navVO.setChildren(children);
                    }
                    return navVO;
                }).toList();
    }

    private List<OfficialWebsitePublicNavVO> buildPublicNavTree(Long parentId, List<OfficialWebsiteNav> navList) {
        return CollectionUtil.emptyIfNull(navList)
                .stream()
                .filter(nav -> {
                    // 根节点：parentId为null或0
                    if (parentId.equals(SystemConstants.ROOT_NODE_ID)) {
                        return nav.getParentId() == null || nav.getParentId().equals(SystemConstants.ROOT_NODE_ID);
                    }
                    // 子节点：parentId等于传入的parentId
                    return nav.getParentId() != null && nav.getParentId().equals(parentId);
                })
                .map(entity -> {
                    OfficialWebsitePublicNavVO publicNavVO = new OfficialWebsitePublicNavVO();
                    publicNavVO.setLabel(entity.getTitle());
                    publicNavVO.setTo(entity.getLinkTo());
                    
                    // 根据类型设置target属性：1-外链(_blank)，2-路由(null或_self)
                    if (entity.getType() != null && entity.getType() == 1) {
                        publicNavVO.setTarget("_blank");
                    }
                    
                    // 递归构建子菜单
                    List<OfficialWebsitePublicNavVO> children = buildPublicNavTree(entity.getId(), navList);
                    if (!children.isEmpty()) {
                        publicNavVO.setChildren(children);
                    }
                    
                    return publicNavVO;
                })
                .collect(Collectors.toList());
    }


}
