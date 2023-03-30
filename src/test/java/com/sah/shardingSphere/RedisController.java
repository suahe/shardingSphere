package com.sah.shardingSphere;

import com.sah.shardingSphere.redis.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author suahe
 * @date 2023/3/30
 * @ApiNote
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void stringTest() {
        String key = "test_key";
        boolean bo = redisUtil.set(key, "test", 1000L);
        if (bo) {
            String str = (String) redisUtil.get(key);
            System.out.println(str);
        }

    }
}
