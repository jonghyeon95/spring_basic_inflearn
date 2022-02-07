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
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();


}
