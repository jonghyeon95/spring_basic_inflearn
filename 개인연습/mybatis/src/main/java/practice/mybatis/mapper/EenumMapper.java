package practice.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import practice.mybatis.domain.Eenum;
import practice.mybatis.domain.EenumDto;

import java.util.List;

@Mapper
public interface EenumMapper {

    List<EenumDto> getEenumList();

    int addEenum(EenumDto eenum);

}
