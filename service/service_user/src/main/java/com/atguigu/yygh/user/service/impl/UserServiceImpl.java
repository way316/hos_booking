package com.atguigu.yygh.user.service.impl;

import com.atguigu.yygh.cmn.common.exception.YyghException;
import com.atguigu.yygh.cmn.common.helpser.JwtHelper;
import com.atguigu.yygh.cmn.common.result.ResultCodeEnum;
import com.atguigu.yygh.cmn.enums.AuthStatusEnum;
import com.atguigu.yygh.cmn.model.hosp.HospitalSet;
import com.atguigu.yygh.cmn.model.user.Patient;
import com.atguigu.yygh.cmn.model.user.User;
import com.atguigu.yygh.cmn.model.user.UserInfo;
import com.atguigu.yygh.cmn.vo.user.LoginVo;
import com.atguigu.yygh.cmn.vo.user.UserAuthVo;
import com.atguigu.yygh.cmn.vo.user.UserInfoQueryVo;
import com.atguigu.yygh.user.mapper.UserMapper;
import com.atguigu.yygh.user.service.PatientService;
import com.atguigu.yygh.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PatientService patientService;

    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {
        String username = loginVo.getId();
        String userPsw = loginVo.getPw();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(userPsw)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        User userFound = baseMapper.selectOne(wrapper);
        if(userFound == null) {
            throw new YyghException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
        if(!userFound.getUserPsw().equals(userPsw)) {
            throw new YyghException(ResultCodeEnum.PASSWORD_WRONG);
        }
        String token = JwtHelper.createToken(userFound.getId(), userFound.getUserName());
        Map<String, Object> map = new HashMap<>();
        map.put("name",userFound.getUserName());
        map.put("token",token);
        return map;
    }

    @Override
    public void userAuth(Long userId, UserAuthVo userAuthVo) {
        //????????????id??????????????????
        User user = baseMapper.selectById(userId);
        //??????????????????
        //???????????????
        user.setName(userAuthVo.getName());
        //??????????????????
        user.setCertificatesType(userAuthVo.getCertificatesType());
        user.setCertificatesNo(userAuthVo.getCertificatesNo());
        user.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        user.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //??????????????????
        baseMapper.updateById(user);
    }

    //???????????????????????????????????????
    @Override
    public IPage<User> selectPage(Page<User> pageParam, UserInfoQueryVo userInfoQueryVo) {
        //UserInfoQueryVo???????????????
        String name = userInfoQueryVo.getKeyword(); //????????????
        Integer status = userInfoQueryVo.getStatus();//????????????
        Integer authStatus = userInfoQueryVo.getAuthStatus(); //????????????
        String createTimeBegin = userInfoQueryVo.getCreateTimeBegin(); //????????????
        String createTimeEnd = userInfoQueryVo.getCreateTimeEnd(); //????????????
        //??????????????????????????????
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(authStatus)) {
            wrapper.eq("auth_status",authStatus);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //??????mapper?????????
        IPage<User> pages = baseMapper.selectPage(pageParam, wrapper);
        //???????????????????????????
        pages.getRecords().stream().forEach(item -> {
            this.packageUserInfo(item);
        });
        return pages;
    }

    //????????????
    @Override
    public void lock(Long userId, Integer status) {
        if(status.intValue()==0 || status.intValue()==1) {
            User user = baseMapper.selectById(userId);
            user.setStatus(status.toString());
            baseMapper.updateById(user);
        }
    }

    //????????????
    @Override
    public Map<String, Object> show(Long userId) {
        Map<String,Object> map = new HashMap<>();
        //??????userid??????????????????
        User user = this.packageUserInfo(baseMapper.selectById(userId));
        map.put("userInfo",user);
        //??????userid?????????????????????
        List<Patient> patientList = patientService.findAllUserId(userId);
        map.put("patientList",patientList);
        return map;
    }

    //????????????  2??????  -1?????????
    @Override
    public void approval(Long userId, Integer authStatus) {
        if(authStatus.intValue()==2 || authStatus.intValue()==-1) {
            User user = baseMapper.selectById(userId);
            user.setAuthStatus(authStatus);
            baseMapper.updateById(user);
        }
    }

    //???????????????????????????
    private User packageUserInfo(User user) {
        //????????????????????????
        user.getParam().put("authStatusString",AuthStatusEnum.getStatusNameByStatus(user.getAuthStatus()));
        //?????????????????? 0  1
        String statusString = Integer.valueOf(user.getStatus()).intValue()==0 ?"??????" : "??????";
        user.getParam().put("statusString",statusString);
        return user;
    }
}
