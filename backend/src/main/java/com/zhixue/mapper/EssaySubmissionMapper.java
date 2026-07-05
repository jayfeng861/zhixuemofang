package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.EssaySubmission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作文提交Mapper接口
 * <p>
 * 提供作文提交表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface EssaySubmissionMapper extends BaseMapper<EssaySubmission> {
}
