package practice.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import practice.mybatis.domain.MemberDto;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<MemberDto> getMemberList();

    MemberDto getMemberById(Integer id);

    List<MemberDto> search(MemberDto memberDto);

    Long addMember(MemberDto memberDto);

    Long addMembers(List<MemberDto> memberDtoList);

    Integer updateMember(MemberDto memberDto);
}
