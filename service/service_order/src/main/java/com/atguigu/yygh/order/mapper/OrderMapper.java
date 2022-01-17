package com.atguigu.yygh.order.mapper;

import com.atguigu.yygh.cmn.model.order.OrderInfo;
import com.atguigu.yygh.cmn.vo.order.OrderCountQueryVo;
import com.atguigu.yygh.cmn.vo.order.OrderCountVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderInfo> {

    //查询预约统计数据的方法
    OrderCountVo selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}

