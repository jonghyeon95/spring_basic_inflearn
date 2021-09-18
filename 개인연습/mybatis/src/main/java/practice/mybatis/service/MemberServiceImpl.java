package practice.mybatis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mybatis.domain.MemberDto;
import practice.mybatis.mapper.MemberMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl{

    private final MemberMapper memberMapper;

    public List<MemberDto> getMemberList() {
        return memberMapper.getMemberList();
    }

    public MemberDto getMemberById(Integer id){
        return memberMapper.getMemberById(id);
    }

    public List<MemberDto> search(MemberDto memberDto){
        return memberMapper.search(memberDto);
    }


    public Long addMember(MemberDto memberDto) {
        return memberMapper.addMember(memberDto); //이거가 안돼
    }

    public Long addMembers(List<MemberDto> memberDtoList) {
        return memberMapper.addMembers(memberDtoList);
    }

    public Integer updateMember(MemberDto memberDto) {
        return memberMapper.updateMember(memberDto);
    }

}
