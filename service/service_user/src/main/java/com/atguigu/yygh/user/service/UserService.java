package com.atguigu.yygh.user.service;

import com.atguigu.yygh.cmn.model.user.User;
import com.atguigu.yygh.cmn.model.user.UserInfo;
import com.atguigu.yygh.cmn.vo.user.LoginVo;
import com.atguigu.yygh.cmn.vo.user.UserAuthVo;
import com.atguigu.yygh.cmn.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Controller;

import java.util.Map;


public interface UserService extends IService<User> {

    Map<String, Object> loginUser(LoginVo loginVo);

    void userAuth(Long userId, UserAuthVo userAuthVo);

    Map<String, Object> show(Long userId);

    void approval(Long userId, Integer authStatus);

    void lock(Long userId, Integer status);

    IPage<User> selectPage(Page<User> pageParam, UserInfoQueryVo userInfoQueryVo);
}
