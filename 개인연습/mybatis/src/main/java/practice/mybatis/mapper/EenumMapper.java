package practice.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import practice.mybatis.domain.Eenum;
import practice.mybatis.domain.EenumDto;
import practice.mybatis.domain.MemberDto;

import java.util.List;
import java.util.Map;

@Mapper
public interface EenumMapper {

    List<EenumDto> getEenumList();

    int addEenum(EenumDto eenum);

    List<Map> selectJoin();

    List<Map> search(@Param("eenum") EenumDto eenumDto,  @Param("member") MemberDto memberDto);

}
