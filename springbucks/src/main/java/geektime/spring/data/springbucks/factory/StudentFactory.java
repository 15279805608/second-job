package geektime.spring.data.springbucks.factory;

import geektime.spring.data.springbucks.model.Student;

/**
 * @author wujun
 * @date 2022/11/17 15:58
 */
public interface StudentFactory {

    default Student createUser() {
        Student student = new Student();
        student.setId(1L);
        student.setName("");
        return student;
    }
}


