package jpabasic.ex1hellojpa;

import lombok.*;

import javax.persistence.*;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "address")
@Entity
public class AddressEntity {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

}
