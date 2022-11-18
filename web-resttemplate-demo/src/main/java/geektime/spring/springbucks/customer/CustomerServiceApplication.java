package geektime.spring.springbucks.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class CustomerServiceApplication implements ApplicationRunner {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(CustomerServiceApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String coffeeUri = "http://localhost:8080/coffee/findInfoJson";
        String s = restTemplate.getForObject(coffeeUri, String.class);
        log.info("String: {}", s);

        String coffeeUri2 = "http://localhost:8080/coffee/findInfoXml";
        String s2 = restTemplate.getForObject(coffeeUri2, String.class);
        log.info("String2: {}", s2);
    }
}
