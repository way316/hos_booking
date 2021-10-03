package com.atguigu.yygh.cmn.model.user;

import com.atguigu.yygh.cmn.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@ApiModel(description = "UserInfo")
@TableName("user")
public class User extends BaseEntity {

    @ApiModelProperty(value = "微信openid")
    @TableField("id")
    private String userID;

    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    @TableField("user_psw")
    private String userPsw;

    @ApiModelProperty(value = "用户名字")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "证件类型")
    @TableField("certificates_type")
    private String certificatesType;

    @ApiModelProperty(value = "证件编号")
    @TableField("certificates_no")
    private String certificatesNo;

    @ApiModelProperty(value = "证件路径")
    @TableField("certificates_url")
    private String certificatesUrl;

    @ApiModelProperty(value = "认证状态（0：未认证 1：认证中 2：认证成功 -1：认证失败）")
    @TableField("auth_status")
    private Integer authStatus;
}
