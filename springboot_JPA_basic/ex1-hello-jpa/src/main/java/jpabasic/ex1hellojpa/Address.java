package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class Address{

    private String city;
    private String street;
    private String zipcode;
}
