package com.atguigu.yygh.cmn.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.cmn.client.DictFeignClient;
import com.atguigu.yygh.cmn.hosp.repository.HospitalRepository;
import com.atguigu.yygh.cmn.hosp.service.HospitalService;
import com.atguigu.yygh.cmn.model.hosp.Hospital;
import com.atguigu.yygh.cmn.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> paramMap) {
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        if (hospitalExist != null) {
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }

    }

    @Override
    public Hospital getByHoscode(String hoscode) {
         Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
         return hospital;
    }

    @Override
    public Page selectHospPage(int page, int limit, HospitalQueryVo hospitalQueryVo) {
        Pageable pageable = PageRequest.of(page-1,limit);
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospital,hospitalQueryVo);
        Example<Hospital> example = Example.of(hospital,matcher);
        Page<Hospital> all = hospitalRepository.findAll(example,pageable);
        List<Hospital> content = all.getContent();
        for (Hospital hos : content) {
            hos.getParam().put("hosTypeString",dictFeignClient.getName("",hos.getHostype()));
            hos.getParam().put("fullAddress",
                    dictFeignClient.getName(hos.getProvinceCode())
                    + dictFeignClient.getName(hos.getCityCode())
                    + dictFeignClient.getName(hos.getDistrictCode()));
        }
        return all;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        //??????id??????????????????
        Hospital hospital = hospitalRepository.findById(id).get();
        //??????????????????
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public Map<String, Object> getHospById(String id) {
        Map<String, Object> result = new HashMap<>();
        Hospital hospital = this.setHospitalHosType(hospitalRepository.findById(id).get());
        //??????????????????????????????????????????
        result.put("hospital",hospital);
        //?????????????????????
        result.put("bookingRule", hospital.getBookingRule());
        //?????????????????????
        hospital.setBookingRule(null);
        return result;
    }

    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if(hospital != null) {
            return hospital.getHosname();
        }
        return null;
    }

    //????????????????????????
    @Override
    public List<Hospital> findByHosname(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }


    //????????????list???????????????????????????????????????
    private Hospital setHospitalHosType(Hospital hospital) {
        //??????dictCode???value????????????????????????
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());
        //????????? ???  ??????
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("fullAddress",provinceString+cityString+districtString);
        hospital.getParam().put("hostypeString",hostypeString);
        return hospital;
    }

    //????????????????????????????????????????????????
    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> result = new HashMap<>();
        //????????????
        Hospital hospital = this.setHospitalHosType(this.getByHoscode(hoscode));
        result.put("hospital", hospital);
        //????????????
        result.put("bookingRule", hospital.getBookingRule());
        //?????????????????????
        hospital.setBookingRule(null);
        return result;
    }
}
