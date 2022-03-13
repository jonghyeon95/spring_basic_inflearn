package study.datajpa.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.Entity.Member;
import study.datajpa.Repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMembers(@PathVariable("id") Long id) {

        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMembers2(@PathVariable("id") Member member) {

        return member.getUsername();
    }

    @PostConstruct
    public void init(){
        memberRepository.save(Member.builder().username("memberA").build());
    }

}
