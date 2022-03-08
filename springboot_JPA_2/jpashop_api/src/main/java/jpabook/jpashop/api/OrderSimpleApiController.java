package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Dto.OrderSearch;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllBySearch(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();    //프록시객체 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }


}
