package practice.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import practice.mybatis.dto.MemberDto;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<MemberDto> getUserList();
}
