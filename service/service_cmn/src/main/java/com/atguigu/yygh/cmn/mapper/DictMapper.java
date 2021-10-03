package com.atguigu.yygh.cmn.mapper;

import com.atguigu.yygh.cmn.model.cmn.Dict;
import com.atguigu.yygh.cmn.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {

}
