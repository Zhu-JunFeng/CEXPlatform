package com.cexpay.common.model;

import com.cexpay.common.constant.SystemConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询请求对象
 * <p>
 * 用于封装分页查询的请求参数，包含页码、每页条数等信息。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {

    /**
     * 页码（从1开始）
     * 默认值为1
     */
    @Builder.Default
    private Integer page = SystemConstants.DEFAULT_PAGE;

    /**
     * 每页条数
     * 默认值为20，最大不超过1000
     */
    @Builder.Default
    private Integer pageSize = SystemConstants.DEFAULT_PAGE_SIZE;

    /**
     * 验证并修正分页参数
     * <p>
     * 确保页码和每页条数在合理范围内：
     * - 页码最小值为1
     * - 每页条数在1-1000之间
     * </p>
     */
    public void validate() {
        if (page == null || page < 1) {
            page = SystemConstants.DEFAULT_PAGE;
        }

        if (pageSize == null || pageSize < SystemConstants.MIN_PAGE_SIZE) {
            pageSize = SystemConstants.DEFAULT_PAGE_SIZE;
        }

        if (pageSize > SystemConstants.MAX_PAGE_SIZE) {
            pageSize = SystemConstants.MAX_PAGE_SIZE;
        }
    }

    /**
     * 获取数据库查询的偏移量
     *
     * @return 偏移量 = (页码 - 1) * 每页条数
     */
    public Integer getOffset() {
        return (page - 1) * pageSize;
    }
}
