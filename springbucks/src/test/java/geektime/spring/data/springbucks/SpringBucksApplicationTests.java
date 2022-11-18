package geektime.spring.data.springbucks;

import com.github.pagehelper.PageInfo;
import geektime.spring.data.springbucks.controller.CoffeeController;
import geektime.spring.data.springbucks.factory.AutowireFactory;
import geektime.spring.data.springbucks.factory.StudentFactory;
import geektime.spring.data.springbucks.model.Coffee;
import geektime.spring.data.springbucks.model.CoffeeOrder;
import geektime.spring.data.springbucks.model.OrderState;
import geektime.spring.data.springbucks.model.Student;
import geektime.spring.data.springbucks.myexception.RollbackException;
import geektime.spring.data.springbucks.service.CoffeeOrderService;
import geektime.spring.data.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBucksApplicationTests {

    @Autowired
    CoffeeController controller;

    /**
     * 测试spring bean 的装配
     */
    @Test
    public void testSpringBean() {
        // 使用xml装配
        ApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        // 1.构造方法
        Student studentByConstructor = xmlApplicationContext.getBean("student-by-constructor", Student.class);
        System.out.println(studentByConstructor);
        // 2.Setter方法
        Student studentBySetter = xmlApplicationContext.getBean("student-by-setter", Student.class);
        System.out.println(studentBySetter);
        // 3.p命名空间方法
        Student studentByNamespace = xmlApplicationContext.getBean("student-by-p-namespace", Student.class);
        System.out.println(studentByNamespace);
        // 4.自定义factoryBean
        Student studentByFactoryBean = xmlApplicationContext.getBean("student-by-factory-bean", Student.class);
        System.out.println(studentByFactoryBean);
        // 5.使用静态方法装配
        Student studentByStaticMethod = xmlApplicationContext.getBean("student-by-static-method", Student.class);
        System.out.println(studentByStaticMethod);
        // 6.AutowireCapableBeanFactory
        AutowireCapableBeanFactory autowireCapableBeanFactory = xmlApplicationContext.getAutowireCapableBeanFactory();
        StudentFactory studentFactory = autowireCapableBeanFactory.createBean(AutowireFactory.class);
        System.out.println(studentFactory.createUser());
    }

}

