package geektime.spring.data.springbucks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wujun
 * @date 2022/11/17 15:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    private Long id;
    private String name;

    public static Student createUser() {
        Student student = new Student();
        student.setId(5L);
        student.setName("静态方法装配");
        return student;
    }
}
