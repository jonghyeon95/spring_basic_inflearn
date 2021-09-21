package practice.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import practice.mybatis.domain.Eenum;
import practice.mybatis.domain.EenumDto;
import practice.mybatis.domain.MemberDto;
import practice.mybatis.mapper.EenumMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
class EenumServiceImplTest {

    @Autowired
    EenumServiceImpl eenumService;

    @Test
    void addEenum() {

        EenumDto eenumDto = new EenumDto();
        eenumDto.setEenum(Eenum.AAA);
        eenumDto.setMemberId(16);

        final int i = eenumService.AddEenum(eenumDto);
        log.info("결과 : {}", i);
    }

    @Test
    void selectEenum() {

        final List<EenumDto> eenums = eenumService.selectEenum();
        for (EenumDto eenum : eenums) {
            log.info("eenum : {}", eenum.getEenum());
        }
    }

    @Test
    void selectJoin(){
        List<Map> maps = eenumService.selectJoin();
        List<MemberDto> members = new ArrayList<>();
        for (Map map : maps) {
            log.info("map : {}",map);
            log.info("name:{}" ,map.get("name"));
            members.add(MemberDtoMapping(map));
        }

        for (MemberDto member : members) {
            log.info("memberDto : {}",member.toString());
        }
    }

    @Test
    void SearchJoin(){
        MemberDto memberDto = new MemberDto();
        EenumDto eenumDto = new EenumDto();
        memberDto.setName("김");
        final List<Map> findMembers = eenumService.search(eenumDto, memberDto);

        for (Map findMember : findMembers) {
            log.info("findMember = {}", findMember);
        }

    }

    MemberDto MemberDtoMapping(Map map){
        MemberDto memberDto = new MemberDto((Integer) map.get("memberId"), (String)map.get("name"), (Integer) map.get("age"), (String)map.get("email"));
//        log.info("memberDto : {}",memberDto.toString());
        return memberDto;
    }

}