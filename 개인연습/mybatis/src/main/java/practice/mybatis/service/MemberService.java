package practice.mybatis.service;

import practice.mybatis.domain.MemberDto;

import java.util.List;

public interface MemberService {

    List<MemberDto> getMemberList();

    Integer addMember(MemberDto memberDto);

    Integer updateMember();
}
