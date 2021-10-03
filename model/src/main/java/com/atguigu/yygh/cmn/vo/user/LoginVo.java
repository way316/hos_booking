package com.atguigu.yygh.cmn.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="登录对象")
public class LoginVo {

    @ApiModelProperty(value = "openid")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String pw;

    @ApiModelProperty(value = "IP")
    private String ip;
}
