package com.official.boot.website.controller;

import com.official.boot.common.model.Option;
import com.official.boot.website.model.vo.OfficialWebsitePublicNavVO;
import com.official.boot.website.service.OfficialWebsiteNavService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.official.boot.website.model.form.OfficialWebsiteNavForm;
import com.official.boot.website.model.query.OfficialWebsiteNavQuery;
import com.official.boot.website.model.vo.OfficialWebsiteNavVO;
import com.official.boot.core.web.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 官网动态导航前端控制层
 *
 * @author offcicaltech
 * @since 2025-12-08 18:07
 */
@Tag(name = "官网动态导航接口")
@RestController
@RequestMapping("/api/v1/official-website-nav")
@RequiredArgsConstructor
public class OfficialWebsiteNavController  {

    private final OfficialWebsiteNavService officialWebsiteNavService;



    @GetMapping("/getAllNavs")
    @Operation(summary = "获取官网导航列表(管理端)")
    @PreAuthorize("@ss.hasPerm('website:official-website-nav:query')")
    public Result<List<OfficialWebsiteNavVO>> getAllNavs(OfficialWebsiteNavQuery queryParams) {
        List<OfficialWebsiteNavVO> result = officialWebsiteNavService.getAllNavs(queryParams);
        return Result.success(result);
    }


    @GetMapping("/getNavs")
    @Operation(summary = "获取官网导航列表(公开)")
    public Result<List<OfficialWebsitePublicNavVO>> getNavs() {
        List<OfficialWebsitePublicNavVO> result = officialWebsiteNavService.getPublicNavs();
        return Result.success(result);
    }


    @Operation(summary = "菜单下拉列表")
    @GetMapping("/getNavOptions")
    public Result<List<Option<Long>>> getNavOptions(
            @Parameter(description = "是否只查询顶级菜单")
            @RequestParam(required = false, defaultValue = "false") boolean onlyTop
    ) {
        List<Option<Long>> menus = officialWebsiteNavService.getNavOptions(onlyTop);
        return Result.success(menus);
    }



    @Operation(summary = "新增官网动态导航")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('website:official-website-nav:add')")
    public Result<Void> saveOfficialWebsiteNav(@RequestBody @Valid OfficialWebsiteNavForm formData ) {
        boolean result = officialWebsiteNavService.saveOfficialWebsiteNav(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取官网动态导航表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('website:official-website-nav:edit')")
    public Result<OfficialWebsiteNavForm> getOfficialWebsiteNavForm(
        @Parameter(description = "官网动态导航ID") @PathVariable Long id
    ) {
        OfficialWebsiteNavForm formData = officialWebsiteNavService.getOfficialWebsiteNavFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "修改官网动态导航")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('website:official-website-nav:edit')")
    public Result<Void> updateOfficialWebsiteNav(
            @Parameter(description = "官网动态导航ID") @PathVariable Long id,
            @RequestBody @Validated OfficialWebsiteNavForm formData
    ) {
        boolean result = officialWebsiteNavService.updateOfficialWebsiteNav(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "删除官网动态导航")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('website:official-website-nav:delete')")
    public Result<Void> deleteOfficialWebsiteNavs(
        @Parameter(description = "官网动态导航ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = officialWebsiteNavService.deleteOfficialWebsiteNavs(ids);
        return Result.judge(result);
    }
}
