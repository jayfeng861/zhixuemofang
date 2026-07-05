package com.zhixue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixue.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * 社区帖子Mapper接口
 * <p>
 * 提供社区帖子表的数据库操作方法，继承MyBatis-Plus BaseMapper。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
