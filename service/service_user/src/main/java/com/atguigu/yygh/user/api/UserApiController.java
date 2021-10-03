package com.atguigu.yygh.user.api;

import com.atguigu.yygh.cmn.common.result.Result;
import com.atguigu.yygh.cmn.common.utils.AuthContextHolder;
import com.atguigu.yygh.cmn.model.user.User;
import com.atguigu.yygh.cmn.model.user.UserInfo;
import com.atguigu.yygh.cmn.vo.user.LoginVo;
import com.atguigu.yygh.cmn.vo.user.UserAuthVo;
import com.atguigu.yygh.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    //用户手机号登录接口
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        Map<String,Object> info = userService.loginUser(loginVo);
        return Result.ok(info);
    }

    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        userService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return Result.ok();
    }

    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        User user = userService.getById(userId);
        return Result.ok(user);
    }
}
