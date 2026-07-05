package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.KnowledgePoint;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识点Mapper接口
 * <p>
 * 提供知识点表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface KnowledgePointMapper extends BaseMapper<KnowledgePoint> {
}
