package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员Mapper接口
 * <p>
 * 提供管理员表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
