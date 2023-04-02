package com.sah.shardingSphere.message.handle;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SendMsgHandle {

    @Resource
    private List<ISendMsgService> sendMsgServices;

    public void sendMsg(String es_receiver, String es_title, String es_content, int type) {
        for (ISendMsgService sendMsgService : sendMsgServices) {
            if (sendMsgService.support(type)) {
                sendMsgService.sendMsg(es_receiver, es_title, es_content);
            }
        }
    }
}
