package com.sah.shardingSphere.message.handle;

public interface ISendMsgService {
	/**
	 * 是否支持类型的消息通知
	 *
	 * @param type
	 * @return
	 */
	boolean support(int type);

	void sendMsg(String es_receiver, String es_title, String es_content);
}
