package practice.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.mybatis.dto.MemberDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class TestServiceImplTest {

    @Autowired
    private MemberService testService;

    @Test
    void getUserList() {

        List<MemberDto> userList = testService.getUserList();

        for (MemberDto testDto : userList) {
            log.info("testDto : {}", testDto);
        }
    }
}