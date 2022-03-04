package jpabook.jpashop.Service;

import jpabook.jpashop.Entity.Member;
import jpabook.jpashop.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {

        if (!validateDuplicateMember(member)) {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }

        return memberRepository.save(member);
    }

    private Boolean validateDuplicateMember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getName());
        return findMembers.isEmpty();
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findByPk(Long member_id) {
        return memberRepository.findByPk(member_id);
    }


}
