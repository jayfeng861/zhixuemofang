package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.WenyanwenScript;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文言文剧本Mapper接口
 * <p>
 * 提供文言文剧本表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface WenyanwenScriptMapper extends BaseMapper<WenyanwenScript> {
}
