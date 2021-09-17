package practice.mybatis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.mybatis.domain.MemberDto;
import practice.mybatis.service.MemberServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;


    @GetMapping("/user")
    public List<MemberDto> getUser(){
        List<MemberDto> userList = memberService.getMemberList();
//        for (TestDto user : userList) {
//            log.info("user = {}",user.getName());
//        }
        return userList;
    }
}
