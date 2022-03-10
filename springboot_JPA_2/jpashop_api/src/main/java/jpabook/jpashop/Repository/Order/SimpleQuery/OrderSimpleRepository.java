package jpabook.jpashop.Repository.Order.SimpleQuery;

import jpabook.jpashop.Dto.OrderSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleRepository {

    private final EntityManager em;

    public List<OrderSimpleDto> findOrderDtos() {

        List<OrderSimpleDto> resultList = em.createQuery(
                "select new jpabook.jpashop.Dto.OrderSimpleDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                        "from Order o join o.member m join o.delivery d",
                OrderSimpleDto.class).getResultList();

        return resultList;
    }
}
