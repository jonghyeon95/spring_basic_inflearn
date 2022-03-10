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

    //===공통===
    private List<OrderQueryDto> findOrders() {
        List<OrderQueryDto> resultList =
                em.createQuery("select new jpabook.jpashop.Repository.Order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o join o.member m join o.delivery d", OrderQueryDto.class)
                        .getResultList();
        return resultList;
    }

    //===V4 where을 이용해 collector 를 처리===
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


    //===V5 where In을 이용해 collector 를 처리===
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

    //===V6 뻥튀긴 된 데이터를 그냥 가져옴===
    public List<OrderFlatDto> findAllByDto_flat() {

        List<OrderFlatDto> result = em.createQuery(
                "select new jpabook.jpashop.Repository.Order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, o.delivery.address, i.name, oi.orderPrice, oi.count) " +
                                "from Order o join o.member m join o.delivery d join o.orderItems oi join oi.item i", OrderFlatDto.class)
                .getResultList();

        return result;
    }
}
