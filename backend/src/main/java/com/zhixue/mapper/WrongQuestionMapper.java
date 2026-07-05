package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.WrongQuestion;
import org.apache.ibatis.annotations.Mapper;

/**
 * 错题Mapper接口
 * <p>
 * 提供错题表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface WrongQuestionMapper extends BaseMapper<WrongQuestion> {
}
