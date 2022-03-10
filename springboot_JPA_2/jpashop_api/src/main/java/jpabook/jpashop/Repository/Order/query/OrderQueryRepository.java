package jpabook.jpashop.Repository.Order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    private List<OrderQueryDto> findOrders() {
        List<OrderQueryDto> resultList =
                em.createQuery("select new jpabook.jpashop.Repository.Order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o join o.member m join o.delivery d", OrderQueryDto.class)
                        .getResultList();
        return resultList;
    }

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();
        result.stream().forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());    //N+1발생
            o.setOrderItems(orderItems);
        });

        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.Repository.Order.query.OrderItemQueryDto(oi.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi join oi.item i where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = toOrdersIds(result);  //orderId 값들을 List 에 담음

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);   //orderId값들은 In 조건으로 검색 후 orderId값을 key값으로 Map에 담음

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems =
                em.createQuery("select new jpabook.jpashop.Repository.Order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi join oi.item i where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream().collect
                (Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));   //key는 orderId, data는 List<OrderItemQueryDto>
        return orderItemMap;
    }

    private List<Long> toOrdersIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream().map(o -> o.getOrderId()).collect(Collectors.toList());
        return orderIds;
    }
}
