package study.datajpa.Repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.Entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom { //class 명명규칙임

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
