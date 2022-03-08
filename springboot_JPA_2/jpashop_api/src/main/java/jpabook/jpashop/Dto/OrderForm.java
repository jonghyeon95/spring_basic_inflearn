package jpabook.jpashop.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderForm {

    private Long memberId;
    private Long itemId;
    private Integer count;

}
