package study.querydsl.Repository;

import study.querydsl.Dto.MemberSearchCondition;
import study.querydsl.Dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCondition condition);
}
