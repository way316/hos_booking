package com.atguigu.yygh.user.controller;

import com.atguigu.yygh.cmn.common.result.Result;
import com.atguigu.yygh.cmn.model.user.UserInfo;
import com.atguigu.yygh.cmn.vo.user.UserInfoQueryVo;
import com.atguigu.yygh.user.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

//    //用户列表（条件查询带分页）
//    @GetMapping("{page}/{limit}")
//    public Result list(@PathVariable Long page,
//                       @PathVariable Long limit,
//                       UserInfoQueryVo userInfoQueryVo) {
//        Page<UserInfo> pageParam = new Page<>(page, limit);
//        IPage<UserInfo> pageModel =
//                userService.selectPage(pageParam, userInfoQueryVo);
//        return Result.ok(pageModel);
//    }

//    //用户详情
//    @GetMapping("show/{userId}")
//    public Result show(@PathVariable Long userId) {
//        Map<String, Object> map = userService.show(userId);
//        return Result.ok(map);
//    }


}