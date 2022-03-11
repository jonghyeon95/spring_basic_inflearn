package study.datajpa.Repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.Dto.MemberDto;
import study.datajpa.Entity.Member;
import study.datajpa.Entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Test
    public void testMember() throws Exception {

        System.out.println("memberRepository = " + memberRepository.getClass());
        //given
        Member member = Member.builder().username("kim").build();

        //when
        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId()).get();

        //then
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() throws Exception {
        Member member1 = Member.builder().username("mem1").build();
        Member member2 = Member.builder().username("mem2").build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception {

        Member m1 = Member.builder().username("aaa").age(10).build();
        Member m2 = Member.builder().username("aaa").age(20).build();

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("aaa", 15);

        for (Member member : result) {
            System.out.println("member = " + member);
        }

        assertThat(result.get(0)).isEqualTo(m2);
    }
    
    @Test
    public void findTop3By() throws Exception {

        List<Member> top3HelloBy = memberRepository.findTop3HelloBy();
        System.out.println("top3HelloBy.size() = " + top3HelloBy.size());
    }

    @Test
    public void namedQuery() throws Exception {
        Member m1 = Member.builder().username("aaa").age(10).build();
        Member m2 = Member.builder().username("aaa").age(20).build();
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("aaa");

        for (Member member : result) {
            System.out.println("member = " + member);
        }

        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    public void testQuery() throws Exception {
        Member m1 = Member.builder().username("aaa").age(10).build();
        Member m2 = Member.builder().username("aaa").age(20).build();
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> findMembers = memberRepository.findUser("aaa", 10);
        assertThat(findMembers.size()).isEqualTo(1);
    }

    @Test
    public void testDtoQuery() throws Exception {
        Member m1 = Member.builder().username("aaa").age(10).build();
        memberRepository.save(m1);

        Team teamA = Team.builder().name("teamA").build();
        teamA.changeTeam(m1);
        teamRepository.save(teamA);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }


}