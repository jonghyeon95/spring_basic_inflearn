package jpabook.jpashop.Service;

import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Enum.OrderStatus;
import jpabook.jpashop.Domain.Item.Book;
import jpabook.jpashop.Domain.Item.Item;
import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Exception.NotEnoughStockException;
import jpabook.jpashop.Repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = Member.builder().name("회원1").address(Address.builder().city("서울").street("강가").zipcode("123-123").build()).build();
        memberService.join(member);
        Item item = Book.builder().name("시골 JPA").price(10000).stockQuantity(10).build();
        itemService.saveItem(item);

        //when
        int orderCnt = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCnt);

        //then
        Order getOrder = orderRepository.findByPk(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * orderCnt, getOrder.getTotalPrice());
        assertEquals(8, item.getStockQuantity());

    }

    @Test
    public void 주문수량초과() throws Exception {
        //given
        Member member = Member.builder().name("회원1").address(Address.builder().city("서울").street("강가").zipcode("123-123").build()).build();
        memberService.join(member);
        Item item = Book.builder().name("시골 JPA").price(10000).stockQuantity(10).build();
        itemService.saveItem(item);

        //when
        int orderCnt = 12;

        //then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCnt);
        });

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = Member.builder().name("회원1").address(Address.builder().city("서울").street("강가").zipcode("123-123").build()).build();
        memberService.join(member);
        Item item = Book.builder().name("시골 JPA").price(10000).stockQuantity(10).build();
        itemService.saveItem(item);

        int orderCnt = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCnt);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findByPk(orderId);
        assertEquals(OrderStatus.CANCEL, order.getStatus());
        assertEquals(10, item.getStockQuantity());

    }
}