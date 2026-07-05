package com.zhixue.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应封装类
 * <p>
 * 封装分页查询的结果数据，包含当前页数据列表、总记录数、当前页码和每页大小。
 * </p>
 *
 * @param <T> 列表数据的泛型类型
 * @author zhixue
 * @version 1.0.0
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页数据列表
     */
    private List<T> list;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Long pageNum;

    /**
     * 每页大小
     */
    private Long pageSize;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 构造分页结果
     *
     * @param list     数据列表
     * @param total    总记录数
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     */
    public PageResult(List<T> list, Long total, Long pageNum, Long pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pageSize > 0 ? (total + pageSize - 1) / pageSize : 0;
    }
}
