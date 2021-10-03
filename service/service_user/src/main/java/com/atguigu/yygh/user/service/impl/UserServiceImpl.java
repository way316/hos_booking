package com.atguigu.yygh.user.service.impl;

import com.atguigu.yygh.cmn.common.exception.YyghException;
import com.atguigu.yygh.cmn.common.helpser.JwtHelper;
import com.atguigu.yygh.cmn.common.result.ResultCodeEnum;
import com.atguigu.yygh.cmn.enums.AuthStatusEnum;
import com.atguigu.yygh.cmn.model.hosp.HospitalSet;
import com.atguigu.yygh.cmn.model.user.User;
import com.atguigu.yygh.cmn.model.user.UserInfo;
import com.atguigu.yygh.cmn.vo.user.LoginVo;
import com.atguigu.yygh.cmn.vo.user.UserAuthVo;
import com.atguigu.yygh.user.mapper.UserMapper;
import com.atguigu.yygh.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


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
        //根据用户id查询用户信息
        User user = baseMapper.selectById(userId);
        //设置认证信息
        //认证人姓名
        user.setName(userAuthVo.getName());
        //其他认证信息
        user.setCertificatesType(userAuthVo.getCertificatesType());
        user.setCertificatesNo(userAuthVo.getCertificatesNo());
        user.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        user.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //进行信息更新
        baseMapper.updateById(user);
    }

}
