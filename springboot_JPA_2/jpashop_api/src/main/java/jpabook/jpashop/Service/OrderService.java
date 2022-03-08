package jpabook.jpashop.Service;

import jpabook.jpashop.Domain.Delivery;
import jpabook.jpashop.Domain.Enum.DeliveryStatus;
import jpabook.jpashop.Domain.Item.Item;
import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Domain.OrderItem;
import jpabook.jpashop.Dto.OrderSearch;
import jpabook.jpashop.Repository.ItemRepository;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findByPk(memberId);
        Item item = itemRepository.findByPk(itemId);
        Delivery delivery = Delivery.builder().status(DeliveryStatus.READY).address(member.getAddress()).build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findByPk(orderId);
        order.cancelOrder();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {

        List<Order> orders = orderRepository.findAllBySearch(orderSearch);
        return orders;
    }

}
