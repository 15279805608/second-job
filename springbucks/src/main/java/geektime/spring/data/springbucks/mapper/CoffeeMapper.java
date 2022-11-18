package geektime.spring.data.springbucks.mapper;

import geektime.spring.data.springbucks.model.Coffee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoffeeMapper {

    List<Coffee> findAllWithParam(@Param("list") List<Integer> id, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
