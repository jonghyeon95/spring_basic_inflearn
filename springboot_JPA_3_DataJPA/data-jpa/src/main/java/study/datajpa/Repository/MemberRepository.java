package study.datajpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.Dto.MemberDto;
import study.datajpa.Entity.Member;

import java.util.List;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();

    //=======Named쿼리=======
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    //=======새로정의=======
    @Query("select m from Member m where m.username=:username and m.age=:age")
    List<Member> findUser(@Param("username")String username, @Param("age") int age);

    //=======String 출력=======
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //=======Dto 출력=======
    @Query("select new study.datajpa.Dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

}
