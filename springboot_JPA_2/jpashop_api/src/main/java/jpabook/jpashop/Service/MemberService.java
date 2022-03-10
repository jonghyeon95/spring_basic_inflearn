package jpabook.jpashop.Service;

import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.Repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepositoryOld memberRepositoryOld;
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {

        if (!validateDuplicateMember(member)) {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }
        Member save = memberRepository.save(member);
        return save.getId();
    }

    private Boolean validateDuplicateMember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getName());
        return findMembers.isEmpty();
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findByPk(Long member_id) {
//        Member member = memberRepositoryOld.findById(member_id);
        return memberRepository.findById(member_id).get();
    }

    @Transactional
    public void update(Long id, String name) {
//        Member member = memberRepositoryOld.findById(id);
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
