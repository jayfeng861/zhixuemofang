package com.zhixue.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询DTO
 * <p>
 * 通用分页查询参数，所有列表接口均支持分页。
 * 默认每页10条，从第1页开始。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
public class PageQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码，默认第1页
     */
    private Integer pageNum = 1;

    /**
     * 每页大小，默认10条
     */
    private Integer pageSize = 10;
}
