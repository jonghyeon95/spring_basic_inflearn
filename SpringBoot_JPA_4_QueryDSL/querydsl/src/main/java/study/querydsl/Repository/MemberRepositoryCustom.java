package study.querydsl.Repository;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import study.querydsl.Dto.MemberSearchCondition;
import study.querydsl.Dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCondition condition);

    PageImpl<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);

    PageImpl<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);
}
