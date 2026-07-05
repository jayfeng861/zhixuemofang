package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.DailyCheckin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 每日打卡Mapper接口
 * <p>
 * 提供每日打卡表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface DailyCheckinMapper extends BaseMapper<DailyCheckin> {
}
