package geektime.spring.data.springbucks.mapper;

import geektime.spring.data.springbucks.model.CoffeeOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoffeeOrderMapper {

    List<CoffeeOrder> CoffeeOrder();

    int save(CoffeeOrder order);

    int update(CoffeeOrder order);

    int delete(CoffeeOrder order);
}
