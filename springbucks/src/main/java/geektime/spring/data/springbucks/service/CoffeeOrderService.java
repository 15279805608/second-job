package geektime.spring.data.springbucks.service;


import geektime.spring.data.springbucks.mapper.CoffeeOrderMapper;
import geektime.spring.data.springbucks.model.Coffee;
import geektime.spring.data.springbucks.model.CoffeeOrder;
import geektime.spring.data.springbucks.model.OrderState;
import geektime.spring.data.springbucks.myexception.RollbackException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderMapper coffeeOrderMapper;

    public List<CoffeeOrder> findAllCoffeeOrder() {
        return coffeeOrderMapper.CoffeeOrder();
    }

    @Transactional(rollbackFor = RollbackException.class)
    public CoffeeOrder createOrder(String customer, Coffee... coffee) throws RollbackException {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffee)))
                .state(OrderState.INIT)
                .build();
        try {
            coffeeOrderMapper.save(order);
            log.info("New Order : {}", order);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RollbackException();
        }
        return order;
    }

    @Transactional(rollbackFor = RollbackException.class)
    public boolean updateState(CoffeeOrder order, OrderState state) throws RollbackException {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        try {
            coffeeOrderMapper.update(order);
            log.info("Updated Order: {}", order);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RollbackException();
        }
        return true;
    }

    @Transactional(rollbackFor = RollbackException.class)
    public boolean delete(CoffeeOrder order) throws RollbackException {
        try {
            coffeeOrderMapper.delete(order);
            log.info("Updated Order: {}", order);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RollbackException();
        }
        return true;
    }
}
