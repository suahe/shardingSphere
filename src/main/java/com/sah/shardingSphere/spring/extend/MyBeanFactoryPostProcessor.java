package com.sah.shardingSphere.spring.extend;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author suahe
 * @date 2023/5/26 16:20
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private String name;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        MyInitializingBean bean = beanFactory.getBean(MyInitializingBean.class);
        name = bean.getName();
    }
}
