package jpabasic.ex1hellojpa;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

//@Data
@Getter //임베디드 타입은 Setter를 없애서 불변하게 만듬
@EqualsAndHashCode
@Builder @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class Address{

    private String city;
    private String street;
    private String zipcode;
}
