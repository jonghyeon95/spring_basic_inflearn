package practice.mybatis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mybatis.domain.Eenum;
import practice.mybatis.domain.EenumDto;
import practice.mybatis.mapper.EenumMapper;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EenumServiceImpl {

    private final EenumMapper eenumMapper;

    int AddEenum(EenumDto eenum){
        return eenumMapper.addEenum(eenum);
    }

    List<EenumDto> selectEenum(){
        return eenumMapper.getEenumList();
    }
}
