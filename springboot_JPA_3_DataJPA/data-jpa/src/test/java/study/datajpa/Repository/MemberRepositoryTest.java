package study.datajpa.Repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.Dto.MemberDto;
import study.datajpa.Dto.NestedClosedProjections;
import study.datajpa.Dto.UsernameOnly;
import study.datajpa.Entity.Member;
import study.datajpa.Entity.Team;
import study.datajpa.Dto.UsernameOnlyDto;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    EntityManager em;

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

        List<Member> result = memberRepository.findNameQueryByUsername("aaa");

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

    @Test
    public void testInQuery() throws Exception {
        Member m1 = Member.builder().username("aaa").age(10).build();
        Member m2 = Member.builder().username("bbb").age(20).build();
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> names = Arrays.asList("aaa", "bbb");

        List<Member> findMembers = memberRepository.findByNames(names);
        for (Member findMember : findMembers) {
            System.out.println("findMember = " + findMember);
        }
    }

    @Test
    public void testReturnType() throws Exception {
        Member m1 = Member.builder().username("aaa").age(10).build();
        Member m2 = Member.builder().username("bbb").age(20).build();
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> findMember1 = memberRepository.findListByUsername("aaa");  //없을때 빈 컬렉션
        Member findMember2 = memberRepository.findMemberByUsername("aaa");  //없을때 null
        Optional<Member> findMember3 = memberRepository.findOptionalByUsername("aaa");
//
        System.out.println("findMember1 = " + findMember1.get(0));
        System.out.println("findMember2 = " + findMember2);
        System.out.println("findMember3 = " + findMember3.orElse(null));
    }

    @Test
    public void testPaging() throws Exception {
        //given
        memberRepository.save(Member.builder().username("a1").age(10).build());
        memberRepository.save(Member.builder().username("a2").age(10).build());
        memberRepository.save(Member.builder().username("a3").age(10).build());
        memberRepository.save(Member.builder().username("a4").age(10).build());
        memberRepository.save(Member.builder().username("a5").age(10).build());
        memberRepository.save(Member.builder().username("a6").age(9).build());

        int age = 10;
        int offset = 0;
        int limit = 3;

        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findPagingByAge(age, pageRequest);

        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        //then
//        List<Member> content = page.getContent();
//        for (Member member : content) {
//            System.out.println("member = " + member);
//        }

        assertThat(page.getTotalElements()).isEqualTo(5);   //전체갯수
        assertThat(page.getNumber()).isEqualTo(0);  //현재페이지
        assertThat(page.getTotalPages()).isEqualTo(2);   //전체페이지
        assertThat(page.isFirst()).isTrue();    //첫번째페이지냐?
        assertThat(page.hasNext()).isTrue();    //다음페이지가 존재하는가?

        assertThat(toMap.getTotalElements()).isEqualTo(5);   //전체갯수
        assertThat(toMap.getNumber()).isEqualTo(0);  //현재페이지
        assertThat(toMap.getTotalPages()).isEqualTo(2);   //전체페이지
        assertThat(toMap.isFirst()).isTrue();    //첫번째페이지냐?
        assertThat(toMap.hasNext()).isTrue();    //다음페이지가 존재하는가?
    }

    @Test
    public void testSlice() throws Exception {
        //given
        memberRepository.save(Member.builder().username("a1").age(10).build());
        memberRepository.save(Member.builder().username("a2").age(10).build());
        memberRepository.save(Member.builder().username("a3").age(10).build());
        memberRepository.save(Member.builder().username("a4").age(10).build());
        memberRepository.save(Member.builder().username("a5").age(10).build());
        memberRepository.save(Member.builder().username("a6").age(9).build());

        int age = 10;
        int offset = 0;
        int limit = 4;

        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "username"));   //limit보다 한개더 가져옴

        //when
        Slice<Member> slice = memberRepository.findSliceByAge(age, pageRequest);

        System.out.println("slice.getSize() = " + slice.getSize());
        System.out.println("slice.getContent().size() = " + slice.getContent().size());
        System.out.println("slice.getNumber() = " + slice.getNumber());
        System.out.println("slice.isFirst() = " + slice.isFirst());
        System.out.println("slice.isLast() = " + slice.isLast());
        System.out.println("slice.hasNext() = " + slice.hasNext());
        System.out.println("slice.hasPrevious() = " + slice.hasPrevious());
    }

    @Test
    public void bulkUpdate() throws Exception {
        //given
        memberRepository.save(Member.builder().username("a1").age(10).build());
        memberRepository.save(Member.builder().username("a2").age(19).build());
        memberRepository.save(Member.builder().username("a3").age(20).build());
        memberRepository.save(Member.builder().username("a4").age(21).build());
        memberRepository.save(Member.builder().username("a5").age(40).build());

        //when
        int resultCount = memberRepository.bulkAgePlus(20);

//        em.clear();

        Member member5 = memberRepository.findMemberByUsername("a5");
        System.out.println("member5 = " + member5);

        //then
        assertThat(resultCount).isEqualTo(3);
    }
    
    @Test
    public void findMemberLazy() throws Exception {
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member memberA = Member.builder().username("member1").age(10).team(teamA).build();
        Member memberB = Member.builder().username("member2").age(10).team(teamB).build();
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        em.flush();
        em.clear();

//        List<Member> members = memberRepository.findAll();
//        List<Member> members = memberRepository.findMemberFetchJoin();
        List<Member> members = memberRepository.findEntityGraphByUsername("member1");
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder().username("member1").age(10).build());
        em.flush();
        em.clear();
        //when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
        em.flush();
        //then
    }

    @Test
    public void queryLock() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder().username("member1").age(10).build());
        em.flush();
        em.clear();
        //when
        List<Member> members = memberRepository.findLockByUsername("member1");

    }

    @Test
    public void callCustom() throws Exception {
        List<Member> memberCustom = memberRepository.findMemberCustom();
        System.out.println("memberCustom = " + memberCustom);
    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        //given
        Member member = Member.builder().username("member1").build();
        memberRepository.save(member);  //@PrePersist 발생

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush(); //@PreUpdate
        em.clear();
        //when
        Member findMember = memberRepository.findById(member.getId()).get();
        //then
        System.out.println("findMember = " + findMember.getCreatedDate());
        System.out.println("findMember = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreatedBy() = " + findMember.getCreatedBy());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
    }

    @Test
    public void queryByExample() throws Exception {
        //given
        Team teamA = Team.builder().name("teamA").build();
        em.persist(teamA);

        Member m1 = Member.builder().username("m1").team(teamA).build();
        Member m2 = Member.builder().username("m2").team(teamA).build();
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        //Probe
        Member searchMember = Member.builder().username("m1").team(Team.builder().name("teamA").build()).build();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("age");
        Example<Member> example = Example.of(searchMember, matcher);
        List<Member> all = memberRepository.findAll(example);
        System.out.println("all = " + all);

        //then

    }

    @Test
    public void projections() throws Exception {
        //given
        Team teamA = Team.builder().name("teamA").build();
        em.persist(teamA);

        Member m1 = Member.builder().username("m1").age(15).team(teamA).build();
        Member m2 = Member.builder().username("m1").age(16).team(teamA).build();
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        List<UsernameOnly> result = memberRepository.findProjectionsInterfaceByUsername("m1");
        for (UsernameOnly username : result) {
            System.out.println("username = " + username.getUsername());
        }
        em.clear();
        List<UsernameOnlyDto> result2 = memberRepository.findProjectionsDtoByUsername("m1");
        for (UsernameOnlyDto usernameOnlyDto : result2) {
            System.out.println("usernameOnlyDto.getUsername() = " + usernameOnlyDto.getUsername());
            System.out.println("usernameOnlyDto.getUsername() = " + usernameOnlyDto.getAge());
        }

        em.clear();
        List<NestedClosedProjections> result3 = memberRepository.findNestedClosedProjectionsByUsername("m1");
        for (NestedClosedProjections nestedClosedProjections : result3) {
            System.out.println("nestedClosedProjections.getUsername() = " + nestedClosedProjections.getUsername());
            System.out.println("nestedClosedProjections.getUsername() = " + nestedClosedProjections.getTeam());
        }
        //then

    }

}