package com.carson.order.cache;


import com.carson.order.domain.Order;
import com.carson.order.repository.OrderRepository;
import com.carson.order.service.OrderService;
import com.github.benmanes.caffeine.cache.Caffeine;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * ClassName CacheManager
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Slf4j
public class CacheManager {

    private final OrderRepository orderRepository;



    public CacheManager(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
        if(loadingCache==null) {
            loadingCache = Caffeine.newBuilder()
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .expireAfterWrite(10, TimeUnit.MINUTES)
                    .weakKeys()
                    .weakValues()
                    .recordStats()//需要手动打开
                    .build(new OrderCacheLoader(orderRepository));
        }
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(new StatsTask(loadingCache),0,1,TimeUnit.MINUTES);
    }


    private LoadingCache<Integer, Order> loadingCache = null;


    public Order getOrder(Integer id){
        return loadingCache.get(id);
    }

    public void refresh(Integer id){
        loadingCache.refresh(id);
    }

    public static class StatsTask implements Runnable{

        private final LoadingCache loadingCache;

        public StatsTask(LoadingCache loadingCache){
            this.loadingCache = loadingCache;
        }
        @Override
        public void run() {
            CacheStats stats = loadingCache.stats();
            log.info("caffeine缓存加载数={},命中率={},命中数={},平均加载时间={}",stats.loadCount(),stats.hitRate(),stats.hitCount(),stats.averageLoadPenalty());

        }
    }
}
