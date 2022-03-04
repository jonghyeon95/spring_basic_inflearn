package jpabook.jpashop.Entity.Item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Data
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;
    private String actor;

}

