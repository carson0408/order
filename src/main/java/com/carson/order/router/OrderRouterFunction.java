package com.carson.order.router;

import com.carson.order.service.OrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
/**
 * ClassName OrderRouterFunction
 *
 * @author carson
 * @description
 * @Version V1.0
 * @createTime
 */
@Configuration
public class OrderRouterFunction {


    @Bean
    public RouterFunction<ServerResponse> routerFunction(OrderHandler orderHandler){
        return RouterFunctions.nest(
                RequestPredicates.path("/flux/routers"),
                RouterFunctions.route(POST("/orders"),orderHandler::orders)
                .andRoute(PUT("/orders/updateAddress"),orderHandler::updateAddress)
                .andRoute(POST("/orders/payment"),orderHandler::payment)
                .andRoute(POST("/orders/delivery"),orderHandler::delivery)
                .andRoute(GET("/orders/getAll"),orderHandler::findAllOrders)
                .andRoute(GET("/orders/{orderId}").and(accept(APPLICATION_JSON)),orderHandler::findByOrderId)
        );
    }
}
