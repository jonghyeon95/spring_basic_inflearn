package practice.mybatis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import practice.mybatis.domain.MemberDto;
import practice.mybatis.mapper.MemberMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl{

    private final MemberMapper memberMapper;

    public List<MemberDto> getMemberList() {
        return memberMapper.getMemberList();
    }

    public Long addMember(MemberDto memberDto) {
        return memberMapper.addMember(memberDto); //이거가 안돼
    }

    public Integer updateMember() {
        return memberMapper.updateMember();
    }

}
