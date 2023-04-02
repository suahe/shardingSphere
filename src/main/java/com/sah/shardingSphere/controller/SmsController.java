package com.sah.shardingSphere.controller;

import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.message.enums.SendMsgTypeEnum;
import com.sah.shardingSphere.message.handle.SendMsgHandle;
import com.sah.shardingSphere.security.NotAuthentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "消息通知接口", tags = "消息通知接口")
@RequestMapping("/sms")
@RestController
public class SmsController {

    @Autowired
    private SendMsgHandle sendMsgHandle;

    @PostMapping("/sendMsg")
    @ApiOperation("发送消息")
    @NotAuthentication
    public CommonResponse sendMsg() {
        sendMsgHandle.sendMsg(null, null, null, SendMsgTypeEnum.WX.getType());
        return CommonResponse.ok();
    }
}
