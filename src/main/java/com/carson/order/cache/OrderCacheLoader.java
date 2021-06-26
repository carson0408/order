package com.carson.order.cache;

import com.carson.order.repository.OrderRepository;
import com.github.benmanes.caffeine.cache.CacheLoader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * ClassName OrderCacheLoader
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */

public class OrderCacheLoader implements CacheLoader {

    private final OrderRepository orderRepository;

    public OrderCacheLoader(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    @Nullable
    @Override
    public Object load(@NonNull Object o) throws Exception {
        return orderRepository.findFirstById((Integer)o);
    }
}
