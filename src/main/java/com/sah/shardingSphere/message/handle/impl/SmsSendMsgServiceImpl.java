package com.sah.shardingSphere.message.handle.impl;

import com.sah.shardingSphere.message.enums.SendMsgTypeEnum;
import com.sah.shardingSphere.message.handle.ISendMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsSendMsgServiceImpl implements ISendMsgService {

	@Override
	public boolean support(int type) {
		return type == SendMsgTypeEnum.SMS.getType();
	}

	@Override
	public void sendMsg(String es_receiver, String es_title, String es_content) {
		// TODO Auto-generated method stub
		log.info("发短信");
	}

}
