package jpabook.jpashop.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Builder @AllArgsConstructor
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
}
