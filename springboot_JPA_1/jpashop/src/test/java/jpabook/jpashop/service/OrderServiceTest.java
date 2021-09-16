package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    EntityManager em;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 상품주문() {
        //given
        Member member = createMember();

        Item item = createBook("정글북",10000,10);

        //When
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        assertThat(item.getPrice() * orderCount).isEqualTo(getOrder.getTotalPrice());
        assertThat(8).isEqualTo(item.getStockQuantity());

    }

    @Test
    void 재고수량초과(){
        //given
        Member member = createMember();
        Item item = createBook("정글북", 10000, 10);

        //when
        int orderCount = 11;

        //then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    @Test
    void 주문취소(){
        //given
        Member member = createMember();
        Item item = createBook("정글북", 10000, 10);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        orderService.cancelOrder(orderId);

        //then
        assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
        assertThat(10).isEqualTo(item.getStockQuantity());

    }



    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }



}