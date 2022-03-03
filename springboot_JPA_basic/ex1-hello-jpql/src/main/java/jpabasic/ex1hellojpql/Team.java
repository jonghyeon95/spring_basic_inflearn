package jpabasic.ex1hellojpql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Team {

    @Id @GeneratedValue
    private Long id;
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();


}
