package practice.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import practice.mybatis.domain.MemberDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = true)
class TestServiceImplTest {

    @Autowired
    private MemberServiceImpl memberService;

    @Test
    void getUserList() {
        List<MemberDto> userList = memberService.getMemberList();

        for (MemberDto testDto : userList) {
            log.info("testDto : {}", testDto);
        }
    }

    @Test
    void findById(){
        final MemberDto findMember = memberService.getMemberById(10);
        log.info("MemberDto = {}", findMember);
    }

    @Test
    void search(){
        MemberDto memberDto = new MemberDto();
        memberDto.setName("김");
        final List<MemberDto> findMembers = memberService.search(memberDto);
        for (MemberDto findMember : findMembers) {
            log.info("findMember = {}", findMember);
        }
    }

    @Test

    void addMember() {
        MemberDto memberDto = new MemberDto();
        memberDto.setAge(28);
        memberDto.setName("심심");
        memberDto.setEmail("wdas@navwer");
        Long id = memberService.addMember(memberDto); //테스트는 이거로

        log.info("id값은 = {}" ,id);
        log.info("memberDto = {}", memberDto);
    }

    @Test
    @Rollback
    void addMembers() {
        MemberDto memberDto1 = new MemberDto();
        memberDto1.setAge(29);
        memberDto1.setName("심심29");
        memberDto1.setEmail("wdas@navwer1");

        MemberDto memberDto2 = new MemberDto();
        memberDto2.setAge(30);
        memberDto2.setName("심심30");
        memberDto2.setEmail("wdas@navwer2");

        List<MemberDto> memberDtoList = new ArrayList<>();
        memberDtoList.add(memberDto1);
        memberDtoList.add(memberDto2);

        Long count = memberService.addMembers(memberDtoList); //테스트는 이거로

        log.info("count = {}" ,count);
        for (MemberDto memberDto : memberDtoList) {
            log.info("memberDto = {}", memberDto);
        }
    }

    @Test
    void updateMember(){
        MemberDto findMember = memberService.getMemberById(10);
        findMember.setName("김김29");

        Integer count = memberService.updateMember(findMember);
        log.info("count = {}" ,count);

        assertThat(findMember).isEqualTo(memberService.getMemberById(10));
    }
}