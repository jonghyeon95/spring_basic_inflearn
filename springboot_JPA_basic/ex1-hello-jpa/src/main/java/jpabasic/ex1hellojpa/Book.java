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
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;

}
