package com.cexpay.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询响应对象
 * <p>
 * 用于封装分页查询的响应结果，包含分页数据、总记录数、总页数等信息。
 * </p>
 *
 * @param <T> 分页数据的类型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页数据列表
     */
    @Builder.Default
    private List<T> data = new ArrayList<>();

    /**
     * 构建分页结果
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @param total    总记录数
     * @param data     当前页数据
     * @param <T>      数据类型
     * @return PageResult对象
     */
    public static <T> PageResult<T> of(Integer page, Integer pageSize, Long total, List<T> data) {
        long totalPage = (total + pageSize - 1) / pageSize;
        return PageResult.<T>builder()
                .page(page)
                .pageSize(pageSize)
                .total(total)
                .totalPage((int) totalPage)
                .data(data)
                .build();
    }

    /**
     * 检查是否存在下一页
     *
     * @return 如果存在下一页返回true
     */
    public boolean hasNext() {
        return page < totalPage;
    }

    /**
     * 检查是否存在上一页
     *
     * @return 如果存在上一页返回true
     */
    public boolean hasPrevious() {
        return page > 1;
    }

    /**
     * 检查是否为第一页
     *
     * @return 如果是第一页返回true
     */
    public boolean isFirstPage() {
        return page == 1;
    }

    /**
     * 检查是否为最后一页
     *
     * @return 如果是最后一页返回true
     */
    public boolean isLastPage() {
        return page >= totalPage;
    }

    /**
     * 获取下一页页码
     *
     * @return 下一页页码，如果已是最后一页则返回当前页码
     */
    public Integer getNextPage() {
        return hasNext() ? page + 1 : page;
    }

    /**
     * 获取上一页页码
     *
     * @return 上一页页码，如果已是第一页则返回当前页码
     */
    public Integer getPreviousPage() {
        return hasPrevious() ? page - 1 : page;
    }

    /**
     * 获取当前页的起始记录编号（从1开始）
     *
     * @return 起始记录编号
     */
    public Long getStartRowNum() {
        return (long) (page - 1) * pageSize + 1;
    }

    /**
     * 获取当前页的结束记录编号
     *
     * @return 结束记录编号
     */
    public Long getEndRowNum() {
        return Math.min((long) page * pageSize, total);
    }

    /**
     * 检查数据是否为空
     *
     * @return 如果数据列表为空返回true
     */
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    /**
     * 获取当前页的数据条数
     *
     * @return 当前页的数据条数
     */
    public Integer getPageDataCount() {
        return data == null ? 0 : data.size();
    }
}
