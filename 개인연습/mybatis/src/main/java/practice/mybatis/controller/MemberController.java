package practice.mybatis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.mybatis.dto.MemberDto;
import practice.mybatis.service.MemberService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService testService;


    @GetMapping("/user")
    public List<MemberDto> getUser(){
        List<MemberDto> userList = testService.getUserList();
//        for (TestDto user : userList) {
//            log.info("user = {}",user.getName());
//        }
        return userList;
    }
}
