package jpabook.jpashop.Service;

import jpabook.jpashop.Domain.Item.Book;
import jpabook.jpashop.Domain.Item.Item;
import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = Member.builder().name("kim1").build();

        //when
        Long savedId = memberService.join(member);
        em.flush();
        //then
        assertEquals(member, memberRepository.findByPk(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = Member.builder().name("kim").build();
        Member member2 = Member.builder().name("kim").build();

        //when
        memberService.join(member1);

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
    
    @Test
    public void 전체회원() throws Exception {
        List<Member> members = memberService.findMembers();
        System.out.println("members.size() = " + members.size());
    }

}