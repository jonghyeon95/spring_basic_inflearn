package study.datajpa.Entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@SuperBuilder @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@ToString(of = {"id", "name"})
@Entity
public class Team extends JpaBaseEntity{

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    public void changeTeam(Member member) {
        members.add(member);
        member.setTeam(this);
    }

}
