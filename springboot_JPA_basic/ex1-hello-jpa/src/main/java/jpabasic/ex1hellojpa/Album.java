package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;

}
