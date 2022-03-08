package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Enum.OrderStatus;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Dto.OrderSearch;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.Service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne 에서의 성능 최적화방법
 * Order
 * Order -> Member N:1
 * Order -> Delivery 1:1
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    //================주문조회 v1=================//
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllBySearch(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();    //프록시객체 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    //================주문조회 v2=================//
    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<Order> orders = orderRepository.findAllBySearch(new OrderSearch());
        List<SimpleOrderDto> collect = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); //Lazy 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //Lazy 초기화
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }



}
