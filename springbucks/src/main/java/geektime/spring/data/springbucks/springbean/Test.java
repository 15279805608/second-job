package geektime.spring.data.springbucks.springbean;

import geektime.spring.data.springbucks.factory.AutowireFactory;
import geektime.spring.data.springbucks.factory.StudentFactory;
import geektime.spring.data.springbucks.model.Student;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wujun
 * @date 2022/11/17 15:41
 */
public class Test {

    /**
     * 测试spring bean 的装配
     *
     * @param args
     */
    public static void main(String[] args) {
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
        StudentFactory userFactory = autowireCapableBeanFactory.createBean(AutowireFactory.class);
        System.out.println(userFactory.createUser());
    }

}
