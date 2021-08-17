package hello.core.member;

import java.util.HashMap;
import java.util.Map;

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
