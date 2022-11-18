package geektime.spring.data.springbucks.controller;

import com.github.pagehelper.PageInfo;
import geektime.spring.data.springbucks.model.Coffee;
import geektime.spring.data.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(path = "/findInfoJson", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public PageInfo<Coffee> findInfoJsom() {
        coffeeService.findAllWithParam(Arrays.asList(1, 2, 3), 1, 3)
                .forEach(c -> log.info("Page(1) Coffee {}", c));
        List<Coffee> list = coffeeService.findAllWithParam(null, 1, 3);
        return new PageInfo<>(list);
    }

    @GetMapping(path = "/findInfoXml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public PageInfo<Coffee> findInfoXml() {
        coffeeService.findAllWithParam(Arrays.asList(1, 2, 3), 1, 3)
                .forEach(c -> log.info("Page(1) Coffee {}", c));
        List<Coffee> list = coffeeService.findAllWithParam(null, 1, 3);
        return new PageInfo<>(list);
    }
}
