package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Enum.OrderStatus;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Domain.OrderItem;
import jpabook.jpashop.Dto.OrderSearch;
import jpabook.jpashop.Repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToMany 조회
 * Order -> OrderItems 1:N
 */

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public Result ordersV1(){   //Entity 직접노출
        List<Order> all = orderRepository.findAllBySearch(new OrderSearch());
        for (Order order : all) {
            order.getMember();
            order.getDelivery().getAddress();
//            for (OrderItem orderItem : order.getOrderItems()) {
//                orderItem.getItem().getName();
//            }
            order.getOrderItems().stream().forEach(o->o.getItem().getName());
        }
        return new Result(all);
    }

    @GetMapping("/api/v2/orders")
    public Result ordersV2(){   //N+1문제
        List<Order> all = orderRepository.findAllBySearch(new OrderSearch());
        List<OrderDto> collect = all.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); //Lazy 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //Lazy 초기화
            orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {

        private String itemName;    //상품명
        private int orderPrice;
        private int count;  //주문수량

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


}
