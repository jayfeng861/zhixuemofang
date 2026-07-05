package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.SysSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统设置Mapper接口
 * <p>
 * 提供系统设置表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface SysSettingMapper extends BaseMapper<SysSetting> {
}
