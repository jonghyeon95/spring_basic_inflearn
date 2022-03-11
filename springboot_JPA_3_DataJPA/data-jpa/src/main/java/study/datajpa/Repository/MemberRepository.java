package study.datajpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.Dto.MemberDto;
import study.datajpa.Entity.Member;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();

    //=======Named쿼리=======
    @Query(name = "Member.findByUsername")
    List<Member> findNameQueryByUsername(@Param("username") String username);

    //=======새로정의=======
    @Query("select m from Member m where m.username=:username and m.age=:age")
    List<Member> findUser(@Param("username")String username, @Param("age") int age);

    //=======String 출력=======
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //=======Dto 출력=======
    @Query("select new study.datajpa.Dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //=======In절 사용=======
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    //=======반환타입=======
    List<Member> findListByUsername(String username);   //컬렉션
    Member findMemberByUsername(String username);   //단건
    Optional<Member> findOptionalByUsername(String username);   //단건 optional

    //=======aa=======
    //=======aa=======
    //=======aa=======
    //=======aa=======


}
