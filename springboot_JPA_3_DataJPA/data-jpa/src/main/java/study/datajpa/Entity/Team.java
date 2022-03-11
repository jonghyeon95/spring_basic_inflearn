package study.datajpa.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@ToString(of = {"id", "name"})
@Entity
public class Team {

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
