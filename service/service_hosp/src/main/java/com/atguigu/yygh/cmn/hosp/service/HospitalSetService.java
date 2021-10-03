package com.atguigu.yygh.cmn.hosp.service;

import com.atguigu.yygh.cmn.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;


public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hospCode);
}
