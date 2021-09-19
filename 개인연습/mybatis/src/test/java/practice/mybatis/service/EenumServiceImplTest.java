package practice.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import practice.mybatis.domain.Eenum;
import practice.mybatis.domain.EenumDto;

import java.util.List;

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
}