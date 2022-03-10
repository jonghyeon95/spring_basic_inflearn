package jpabook.jpashop.Repository.OrderSimpleRepository;

import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Dto.OrderSearch;
import jpabook.jpashop.Dto.OrderSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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
