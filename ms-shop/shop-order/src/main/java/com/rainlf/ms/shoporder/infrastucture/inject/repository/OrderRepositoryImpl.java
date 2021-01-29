package com.rainlf.ms.shoporder.infrastucture.inject.repository;

import com.rainlf.ms.shoporder.domain.model.Order;
import com.rainlf.ms.shoporder.domain.repository.OrderRepository;
import com.rainlf.ms.shoporder.infrastucture.dao.factory.OrderFactory;
import com.rainlf.ms.shoporder.infrastucture.dao.repository.OrderPORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : rain
 * @date : 2021/1/27 19:06
 */
@Service
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderPORepository orderPORepository;

    @Autowired
    private OrderFactory orderFactory;

    @Override
    public void saveOrder(Order order) {
        orderPORepository.save(orderFactory.createOrderPO(order));
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        return orderPORepository.findByUserId(userId).stream().map(orderFactory::createOrder).collect(Collectors.toList());
    }

    @Override
    public Order findById(Integer id) {
        return orderFactory.createOrder(orderPORepository.findById(id).orElse(null));
    }
}
