package com.sah.shardingSphere;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author suahe
 * @date 2023/3/30
 * @ApiNote
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReflectionTest {

    @Test
    public void reflectionTest() throws Exception {
        Class<DemoClass> demoClassClass = DemoClass.class;
        DemoClass bean = getBean(demoClassClass);
        Field filed2 = ReflectionUtils.findField(demoClassClass, "filed");
        filed2.setAccessible(true);
        filed2.set(bean, "测试2");

        Method method = ReflectionUtils.findMethod(demoClassClass, "test", String.class);
        Object obj = ReflectionUtils.invokeMethod(method, bean, "测试");
        System.out.println(obj.toString());
    }

    @Test
    public void reflectionTest2() throws Exception {
        Class<DemoClass> demoClassClass = DemoClass.class;
        DemoClass bean = getBean(demoClassClass);
        Field filed1 = demoClassClass.getDeclaredField("filed");
        filed1.setAccessible(true);
        filed1.set(bean, "测试1");
        Method method = demoClassClass.getMethod("test", String.class);
        method.invoke(bean, "111111");
    }

    private DemoClass getBean(Class<DemoClass> demoClassClass) throws Exception {
        Constructor<DemoClass> declaredConstructor = demoClassClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        DemoClass bean = declaredConstructor.newInstance();
        return bean;
    }
}
