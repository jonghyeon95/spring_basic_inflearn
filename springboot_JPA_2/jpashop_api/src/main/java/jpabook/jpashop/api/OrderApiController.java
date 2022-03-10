package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Domain.OrderItem;
import jpabook.jpashop.Dto.OrderSearch;
import jpabook.jpashop.Repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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




    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
