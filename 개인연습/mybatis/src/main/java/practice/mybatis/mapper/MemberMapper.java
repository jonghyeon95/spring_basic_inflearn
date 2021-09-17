package practice.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import practice.mybatis.domain.MemberDto;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<MemberDto> getMemberList();

    Long addMember(MemberDto memberDto); //Insert 실행 갯수 리턴


    Integer updateMember();
}
