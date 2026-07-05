package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.StudentProfile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生档案Mapper接口
 * <p>
 * 提供学生档案表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface StudentProfileMapper extends BaseMapper<StudentProfile> {
}
