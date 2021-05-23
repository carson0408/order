package com.carson.order.repository;

import com.carson.order.domain.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * ClassName OrderRepository
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
public interface OrderRepository extends CrudRepository<Order,Integer> {
}
