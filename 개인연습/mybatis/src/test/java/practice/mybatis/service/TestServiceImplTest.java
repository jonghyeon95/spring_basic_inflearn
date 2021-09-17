package practice.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.mybatis.domain.MemberDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class TestServiceImplTest {

    @Autowired
    private MemberServiceImpl testService;

    @Test
    void getUserList() {
        List<MemberDto> userList = testService.getMemberList();

        for (MemberDto testDto : userList) {
            log.info("testDto : {}", testDto);
        }
    }

    @Test
    void addMember() {
        MemberDto memberDto = new MemberDto();
        memberDto.setAge(28);
        memberDto.setName("심심");
        memberDto.setEmail("wdas@navwer");
        Long id = testService.addMember(memberDto); //테스트는 이거로

        log.info("id값은 = {}" ,id);
    }

    @Test
    void updateMember(){

    }
}