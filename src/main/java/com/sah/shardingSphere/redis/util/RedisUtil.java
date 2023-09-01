package com.sah.shardingSphere.redis.util;

import com.sah.shardingSphere.exception.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @Author Scott
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("execute expire error:{}", e);
            throw new RedisException(e);
        }
    }


    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                } else {
                    redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
                }
            }
        } catch (Exception e) {
            log.error("execute del error:{}", e);
            throw new RedisException(e);
        }
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("execute get error:{}", e);
            throw new RedisException(e);
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("execute set error:{}", e);
            throw new RedisException(e);
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("execute set error:{}", e);
            throw new RedisException(e);
        }
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hGet(String key, String item) {
        try {
            return redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            log.error("execute hget error:{}", e);
            throw new RedisException(e);
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmGet(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("execute hmget error:{}", e);
            throw new RedisException(e);
        }
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("execute hmset error:{}", e);
            throw new RedisException(e);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("execute hset error:{}", e);
            throw new RedisException(e);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("execute hset error:{}", e);
            throw new RedisException(e);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            log.error("execute hdel error:{}", e);
            throw new RedisException(e);
        }
    }


    /**
     * 获取指定前缀的一系列key
     * 使用scan命令代替keys, Redis是单线程处理，keys命令在KEY数量较多时，
     * 操作效率极低【时间复杂度为O(N)】，该命令一旦执行会严重阻塞线上其它命令的正常请求
     *
     * @param keyPrefix
     * @return
     */
    private Set<String> keys(String keyPrefix) {
        String realKey = keyPrefix + "*";
        try {
            return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                Set<String> binaryKeys = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(realKey).count(Integer.MAX_VALUE).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }

                return binaryKeys;
            });
        } catch (Exception e) {
            log.error("execute keys error:{}", e);
            throw new RedisException(e);
        }
    }

    public Set<String> matchKeys(String keyPrefix) {
        try {
            Set<String> keys = redisTemplate.keys(keyPrefix + "*");
            return keys;
        } catch (Exception e) {
            log.error("execute matchKeys error:{}", e);
            throw new RedisException(e);
        }
    }

    public boolean zAdd(String key, Object value, long score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
            return true;
        } catch (Exception e) {
            log.error("execute zadd error:{}", e);
            throw new RedisException(e);
        }
    }

    public Set rangeByScore(String key, long min, long max) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("execute rangeByScore error:{}", e);
            throw new RedisException(e);
        }
    }

    public Set rangeByScore(String key, long min, long max, long offset, long count) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("execute rangeByScore error:{}", e);
            throw new RedisException(e);
        }
    }

    public Long zRem(String key, Object value) {
        try {
            Long a = redisTemplate.opsForZSet().remove(key, value);
            return a;
        } catch (Exception e) {
            log.error("execute zrem error:{}", e);
            throw new RedisException(e);
        }
    }

    public Double score(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            log.error("execute score error:{}", e);
            throw new RedisException(e);
        }
    }

    /**
     * 根据key前缀批量删除缓存
     *
     * @param key
     * @return
     */
    public long batchDel(String key) {
        try {
            long result = 0;
            Set<String> set = redisTemplate.keys(key + "*");
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                del(keyStr);
            }
            return result;
        } catch (Exception e) {
            log.error("execute batchDel error:{}", e);
            throw new RedisException(e);
        }
    }

    public Long incrBy(String key, Long value) {
        try {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            return redisTemplate.opsForValue().increment(key, value);
        } catch (Exception e) {
            log.error("execute incrBy error:{}", e);
            throw new RedisException(e);
        }
    }
}
