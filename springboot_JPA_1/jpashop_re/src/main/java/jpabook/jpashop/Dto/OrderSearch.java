package jpabook.jpashop.Dto;

import jpabook.jpashop.Domain.Enum.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
