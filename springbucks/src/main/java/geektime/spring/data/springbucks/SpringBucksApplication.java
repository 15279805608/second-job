package geektime.spring.data.springbucks;

import com.github.pagehelper.PageInfo;
import geektime.spring.data.springbucks.model.Coffee;
import geektime.spring.data.springbucks.model.CoffeeOrder;
import geektime.spring.data.springbucks.model.OrderState;
import geektime.spring.data.springbucks.myexception.RollbackException;
import geektime.spring.data.springbucks.service.CoffeeOrderService;
import geektime.spring.data.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableTransactionManagement
@SpringBootApplication
@Slf4j
@MapperScan("geektime.spring.data.springbucks.mapper")
public class SpringBucksApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBucksApplication.class, args);
    }

    @Bean
    public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Coffee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}

