package com.atguigu.yygh.order.service;

import com.atguigu.yygh.cmn.model.order.OrderInfo;
import com.atguigu.yygh.cmn.vo.order.OrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface OrderService {
    Object saveOrder(String scheduleId, Long patientId);

    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    OrderInfo getOrder(String orderId);
}
