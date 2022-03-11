package study.datajpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.datajpa.Entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
