package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.Vocabulary;
import org.apache.ibatis.annotations.Mapper;

/**
 * 词汇Mapper接口
 * <p>
 * 提供词汇表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface VocabularyMapper extends BaseMapper<Vocabulary> {
}
