package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.entity.SysSetting;

import java.util.List;
import java.util.Map;

/**
 * 系统设置服务接口
 * <p>
 * 提供系统设置的查询、更新等业务方法。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
public interface SysSettingService extends IService<SysSetting> {

    /**
     * 获取所有设置列表
     *
     * @return 设置列表
     */
    List<SysSetting> getAllSettings();

    /**
     * 根据分组获取设置列表
     *
     * @param group 分组名
     * @return 设置列表
     */
    List<SysSetting> getSettingsByGroup(String group);

    /**
     * 根据key获取设置值
     *
     * @param key 设置键
     * @return 设置值
     */
    String getSettingValue(String key);

    /**
     * 更新设置
     *
     * @param key   设置键
     * @param value 设置值
     * @return 更新后的设置
     */
    SysSetting updateSetting(String key, String value);

    /**
     * 批量更新设置
     *
     * @param settings 设置Map（key-value）
     */
    void batchUpdateSettings(Map<String, String> settings);
}
