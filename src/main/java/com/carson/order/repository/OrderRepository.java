package com.carson.order.repository;

import com.carson.order.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * ClassName OrderRepository
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
public interface OrderRepository extends CrudRepository<Order,Integer> {
    List<Order> findAll();
    Order findFirstById(Integer id);
}
