package com.zhixue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.common.BusinessException;
import com.zhixue.entity.SysSetting;
import com.zhixue.mapper.SysSettingMapper;
import com.zhixue.service.SysSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统设置服务实现类
 * <p>
 * 实现系统设置的查询、更新等核心业务逻辑。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
public class SysSettingServiceImpl extends ServiceImpl<SysSettingMapper, SysSetting> implements SysSettingService {

    @Override
    public List<SysSetting> getAllSettings() {
        return list();
    }

    @Override
    public List<SysSetting> getSettingsByGroup(String group) {
        return lambdaQuery()
                .eq(SysSetting::getSettingGroup, group)
                .list();
    }

    @Override
    public String getSettingValue(String key) {
        SysSetting setting = lambdaQuery()
                .eq(SysSetting::getSettingKey, key)
                .last("LIMIT 1")
                .one();
        return setting != null ? setting.getSettingValue() : null;
    }

    @Override
    public SysSetting updateSetting(String key, String value) {
        SysSetting setting = lambdaQuery()
                .eq(SysSetting::getSettingKey, key)
                .last("LIMIT 1")
                .one();

        if (setting == null) {
            throw new BusinessException("设置项不存在");
        }

        setting.setSettingValue(value);
        updateById(setting);
        return setting;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateSettings(Map<String, String> settings) {
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            SysSetting setting = lambdaQuery()
                    .eq(SysSetting::getSettingKey, key)
                    .last("LIMIT 1")
                    .one();

            if (setting != null) {
                setting.setSettingValue(value);
                updateById(setting);
            }
        }
    }
}
