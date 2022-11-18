package geektime.spring.data.springbucks.factory;

import geektime.spring.data.springbucks.model.Student;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author wujun
 * @date 2022/11/17 16:38
 */
public class MyFactoryBean implements FactoryBean<Student> {
    @Override
    public Student getObject() throws Exception {
        return new Student(4L, "使用FactoryBean方式注入");
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
