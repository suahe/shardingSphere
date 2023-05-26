package com.sah.shardingSphere.spring.extend;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @author suahe
 * @date 2023/5/26 15:31
 */
@Component
public class MyBeanFactoryAware implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public static <T> T getBean(Class<T> clazz){
        return beanFactory.getBean(clazz);
    }
}
