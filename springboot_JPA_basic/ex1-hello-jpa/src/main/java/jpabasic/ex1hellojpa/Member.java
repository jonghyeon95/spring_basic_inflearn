package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@SuperBuilder @NoArgsConstructor @AllArgsConstructor
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Embedded   //임베디드 타입
    private Address address;

    @Embedded
    private Period period;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start_date", column = @Column(name = "start_date2")),
            @AttributeOverride(name = "end_date", column = @Column(name = "end_date2"))
    })
    private Period period2;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    ///////////////

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setMember(this);
    }

}
