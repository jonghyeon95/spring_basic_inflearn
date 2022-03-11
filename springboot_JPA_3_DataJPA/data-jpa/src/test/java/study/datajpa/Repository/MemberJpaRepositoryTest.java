package study.datajpa.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.Entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() throws Exception {
        //given
        Member member = Member.builder().username("kim").build();

        //when
        Member saveMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(saveMember.getId());

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() throws Exception {
        Member member1 = Member.builder().username("mem1").build();
        Member member2 = Member.builder().username("mem2").build();
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception {

        Member m1 = Member.builder().username("aaa").age(10).build();
        Member m2 = Member.builder().username("aaa").age(20).build();

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("aaa", 15);

        assertThat(result.get(0)).isEqualTo(m2);
    }

    @Test
    public void namedQuery() throws Exception {
        Member m1 = Member.builder().username("aaa").age(10).build();
        Member m2 = Member.builder().username("aaa").age(20).build();

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsername("aaa");

        for (Member member : result) {
            System.out.println("member = " + member);
        }

        assertThat(result.get(0)).isEqualTo(m1);
    }

}