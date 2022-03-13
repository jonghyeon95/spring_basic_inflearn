package study.datajpa.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.Dto.MemberDto;
import study.datajpa.Entity.Member;
import study.datajpa.Entity.Team;
import study.datajpa.Repository.MemberRepository;
import study.datajpa.Repository.TeamRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    //=============도메인 클래스 컨버터=============
    @GetMapping("/members/{id}")
    public String findMembers(@PathVariable("id") Long id) {

        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }
    
    @GetMapping("/members2/{id}")
    public String findMembers2(@PathVariable("id") Member member) { //도메인 클래스 컨버터 적용

        return member.getUsername();
    }

    //=============페이징, 정렬=============
    @GetMapping("/members")
    public Result memberList(@PageableDefault(size = 5, sort = "username") Pageable pageable) {

        Map<String, Object> map = new HashMap<>();
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> dtos = page.map(m -> new MemberDto(m));

        map.put("members", dtos);
        return new Result(map);
    }

    @GetMapping("/membersAndTeam")
    public Result memberTeamList(@Qualifier("member") @PageableDefault(size = 5, sort = "username") Pageable memberPageable,
                                 @Qualifier("team") @PageableDefault(size = 4, sort = "name") Pageable teamPageable) {

        Map<String, Object> map = new HashMap<>();
        Page<Member> page = memberRepository.findAll(memberPageable);
        map.put("members", page);
        Page<Team> teams = teamRepository.findAll(teamPageable);
        map.put("teams", teams);

        return new Result(map);
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{

        private Map<String, T> data;
    }


    @PostConstruct
    public void init(){

        for (int i = 0; i < 10; i++) {
            teamRepository.save(Team.builder().name("team" + i).build());
        }

        for (int i = 0; i < 100; i++) {
            memberRepository.save(Member.builder().username("member" + i).age(i).build());
        }
    }


}
