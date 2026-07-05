package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.LearningRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学习记录Mapper接口
 * <p>
 * 提供学习记录表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface LearningRecordMapper extends BaseMapper<LearningRecord> {
}
