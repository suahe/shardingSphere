package com.sah.shardingSphere.message.handle.impl;

import com.sah.shardingSphere.message.handle.ISendMsgHandle;
import com.sah.shardingSphere.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.sah.shardingSphere.message.entity.StaticConfig;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author suahe
 * @date 2023/3/31
 * @ApiNote
 */
@Slf4j
public class EmailSendMsgHandle implements ISendMsgHandle {

    static String emailFrom;

    public static void setEmailFrom(String emailFrom) {
        EmailSendMsgHandle.emailFrom = emailFrom;
    }

    @Override
    public void SendMsg(String es_receiver, String es_title, String es_content) {
        JavaMailSender mailSender = (JavaMailSender) SpringContextUtil.getBean("mailSender");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        //update-begin-author：taoyan date:20200811 for:配置类数据获取
        if(StringUtils.isEmpty(emailFrom)){
            StaticConfig staticConfig = SpringContextUtil.getBean(StaticConfig.class);
            setEmailFrom(staticConfig.getEmailFrom());
        }
        //update-end-author：taoyan date:20200811 for:配置类数据获取
        try {
            helper = new MimeMessageHelper(message, true);
            // 设置发送方邮箱地址
            helper.setFrom(emailFrom);
            helper.setTo(es_receiver);
            helper.setSubject(es_title);
            helper.setText(es_content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(" mail send error : {}"+ e);
        }

    }
}
