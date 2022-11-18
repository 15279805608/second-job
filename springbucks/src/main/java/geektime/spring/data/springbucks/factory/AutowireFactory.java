package geektime.spring.data.springbucks.factory;

import geektime.spring.data.springbucks.model.Student;

/**
 * @author wujun
 * @date 2022/11/17 15:43
 */


public class AutowireFactory implements StudentFactory {

    @Override
    public Student createUser() {
        Student student = new Student();
        student.setId(6L);
        student.setName("AutowireFactory装配");
        return student;
    }
}

