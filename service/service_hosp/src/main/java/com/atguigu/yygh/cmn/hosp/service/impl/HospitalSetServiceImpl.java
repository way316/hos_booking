package com.atguigu.yygh.cmn.hosp.service.impl;

import com.atguigu.yygh.cmn.hosp.mapper.HospitalSetMapper;
import com.atguigu.yygh.cmn.hosp.service.HospitalSetService;
import com.atguigu.yygh.cmn.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {


    @Override
    public String getSignKey(String hospCode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hospCode);
        HospitalSet hospital = baseMapper.selectOne(wrapper);
        return hospital.getSignKey();
    }
}
