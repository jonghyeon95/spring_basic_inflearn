package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@SuperBuilder @NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;
    private String actor;

}
