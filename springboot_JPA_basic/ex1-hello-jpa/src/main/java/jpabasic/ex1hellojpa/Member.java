package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.*;

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
    private Address homeAddress;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "favorite_food", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

//    @Builder.Default
//    @ElementCollection
//    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "member_id"))
//    private List<Address> addressHistory = new ArrayList<>();

//    @Builder.Default
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "member_id") //일대다 단방향. 관계의 연관관계주인을 1 로 설정
//    private List<AddressEntity> addressHistory = new ArrayList<>();


//    @Embedded
//    private Period period;
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "start_date", column = @Column(name = "start_date2")),
//            @AttributeOverride(name = "end_date", column = @Column(name = "end_date2"))
//    })
//    private Period period2;

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
