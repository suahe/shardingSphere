package com.sah.shardingSphere.queue.rocketmq;

/**
 * @author paulG
 * @since 2020/11/4
 **/
public class RocketmqSendCallbackBuilder {


    public static RocketmqSendCallback commonCallback() {
        return new RocketmqSendCallback();
    }

}
