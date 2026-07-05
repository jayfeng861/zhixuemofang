package com.zhixue.controller;

import com.zhixue.common.Result;
import com.zhixue.entity.SysSetting;
import com.zhixue.service.SysSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统设置控制器
 * <p>
 * 处理系统设置的查询和更新请求。
 * 路径前缀：/api/v1/admin/settings
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/admin/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SysSettingService sysSettingService;

    /**
     * 获取设置列表
     *
     * @param group 分组名称（可选）
     * @return 设置列表
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestParam(required = false) String group) {
        List<SysSetting> settings;
        if (group != null && !group.isEmpty()) {
            settings = sysSettingService.getSettingsByGroup(group);
        } else {
            settings = sysSettingService.getAllSettings();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", settings);
        return Result.success(result);
    }

    /**
     * 更新设置
     *
     * @param params 设置参数，包含key和value
     * @return 更新后的设置
     */
    @PutMapping("/update")
    public Result<SysSetting> update(@RequestBody Map<String, String> params) {
        String key = params.get("key");
        String value = params.get("value");
        return Result.success("更新成功", sysSettingService.updateSetting(key, value));
    }

    /**
     * 批量更新设置
     *
     * @param settings 设置Map（key-value）
     * @return 更新结果
     */
    @PutMapping("/batch")
    public Result<Void> batchUpdate(@RequestBody Map<String, String> settings) {
        sysSettingService.batchUpdateSettings(settings);
        return Result.success("批量更新成功", null);
    }
}
