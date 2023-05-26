package com.sah.shardingSphere.spring.extend;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author suahe
 * @date 2023/5/26 15:24
 */
@Component
public class MyDisposableBean implements DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("bean被销毁时或生命周期结束时，被调用");
        MyInitializingBean myInitializingBean = MyApplicationContextAware.getBean(MyInitializingBean.class);
        if (myInitializingBean != null) {
            String name = myInitializingBean.getName();
            System.out.println("MyInitializingBean对象的name属性值为:" + name);
        }
    }
}
