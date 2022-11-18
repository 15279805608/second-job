package geektime.spring.data.springbucks.service;

import geektime.spring.data.springbucks.mapper.CoffeeMapper;
import geektime.spring.data.springbucks.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wujun
 * @date 2022/10/26 17:09
 */
@Service
@Slf4j
public class CoffeeService {
    @Autowired
    private CoffeeMapper coffeeMapper;
    private static final String CACHE = "springbucks-coffee";
    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    public List<Coffee> findAllWithParam(List<Integer> id, Integer pageNum, Integer pageSize) {
        return coffeeMapper.findAllWithParam(id, pageNum, pageSize);
    }

    public List<Coffee> findCoffee(List<Integer> id) {
        HashOperations<String, List<Integer>, List<Coffee>> hashOperations = redisTemplate.opsForHash();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(CACHE)) && hashOperations.hasKey(CACHE, id)) {
            log.info("Get coffee {} from Redis.", id);
            return hashOperations.get(CACHE, id);
        }
        List<Coffee> list = coffeeMapper.findAllWithParam(id, 2, 3);
        log.info("Coffee Found: {}", list);
        if (!list.isEmpty()) {
            log.info("Put coffee {} to Redis.", id);
            hashOperations.put(CACHE, id, list);
            redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);
        }
        return list;
    }
}
