package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.OralPractice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 口语练习Mapper接口
 * <p>
 * 提供口语练习表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface OralPracticeMapper extends BaseMapper<OralPractice> {
}
