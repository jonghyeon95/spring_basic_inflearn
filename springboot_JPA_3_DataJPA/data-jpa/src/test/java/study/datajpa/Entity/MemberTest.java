package study.datajpa.Entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() throws Exception {
        //given
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();

        Member memberA = Member.builder().username("member1").build();
        Member memberB = Member.builder().username("member2").build();
        Member memberC = Member.builder().username("member3").build();
        Member memberD = Member.builder().username("member4").build();

        teamA.changeTeam(memberA);
        teamA.changeTeam(memberB);
        teamB.changeTeam(memberC);
        teamB.changeTeam(memberD);

        em.persist(teamA);
        em.persist(teamB);

        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
        }

    }

}