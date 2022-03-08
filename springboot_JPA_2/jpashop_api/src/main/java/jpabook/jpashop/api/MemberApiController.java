package jpabook.jpashop.api;

import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //===========회원가입==============//
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = Member.builder().name(request.name).build();
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    //===========회원수정==============//
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@RequestBody @Valid UpdateMemberRequest request, @PathVariable Long id) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findByPk(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    //===========회원조회==============//
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        List<Member> members = memberService.findMembers();
        return members;
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();

//        List<MemberDto> collect = new ArrayList<>();
//        for (Member findMember : findMembers) {
//            collect.add(new MemberDto(findMember.getName(), findMember.getId()));
//        }

        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getName(), m.getId())).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
        private Long id;
    }

}
