package com.yiyue.common.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XiaoQi
 * @Date: 2021/1/18 10:27
 */
public class PageUtil {

    /**
     * 构建分页排序所需参数
     *
     * @param baseDTO
     * @param <T>
     * @return
     */
    public static <T> Page<T> getPagination(BasePageDTO baseDTO) {
        String order = "ascend";
        Page<T> page = new Page(baseDTO.getCurrent(), baseDTO.getSize());
        if (StringUtils.isNotBlank(baseDTO.getSortField())) {
            List<OrderItem> orders = new ArrayList<>();
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(baseDTO.getSortField());
            orderItem.setAsc(order.equalsIgnoreCase(baseDTO.getSortOrder()));
            orders.add(orderItem);
            page.setOrders(orders);
        }
        return page;
    }
}
