package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> stroe = new HashMap<>();

    @Override
    public void save(Member member) {
        stroe.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return stroe.get(memberId);
    }

}
