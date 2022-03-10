package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Dto.OrderSearch;
import jpabook.jpashop.Dto.OrderSimpleDto;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.Repository.Order.SimpleQuery.OrderSimpleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final OrderSimpleRepository orderSimpleRepository;

    //================주문조회 v1 (entity직접 노출)=================//    #entity 직접 노출은 좋지않음
    @GetMapping("/api/v1/simple-orders")
    public Result ordersV1() {
        List<Order> all = orderRepository.findAllBySearch(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();    //프록시객체 강제 초기화
            order.getDelivery().getAddress();
        }
        return new Result(all);
    }

    //================주문조회 v2 (Dto반환)=================// #N+1 문제
    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<Order> orders = orderRepository.findAllBySearch(new OrderSearch());
        List<OrderSimpleDto> collect = orders.stream().map(o -> new OrderSimpleDto(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    //================주문조회 v3 (fetch join)=================//
    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderSimpleDto> collect = orders.stream().map(o -> new OrderSimpleDto(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    //================주문조회 v4 (Repository에서 바로 Dto 반환)=================//   !재사용성이 떨어짐
    @GetMapping("/api/v4/simple-orders")
    public Result ordersV4() {
        List<OrderSimpleDto> orders = orderSimpleRepository.findOrderDtos();
        return new Result(orders);
    }

}
