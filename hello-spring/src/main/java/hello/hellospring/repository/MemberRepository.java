package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id); //Java8 기능, null 처리가능
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
