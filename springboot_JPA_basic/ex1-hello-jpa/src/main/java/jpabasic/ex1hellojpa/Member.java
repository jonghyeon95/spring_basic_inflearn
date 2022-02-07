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
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

//    @Builder.Default
//    @OneToMany(mappedBy = "member")
//    private List<Order> orders = new ArrayList<>();

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
