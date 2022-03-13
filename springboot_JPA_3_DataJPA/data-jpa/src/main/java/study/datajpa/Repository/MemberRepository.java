package study.datajpa.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.Dto.MemberDto;
import study.datajpa.Dto.NestedClosedProjections;
import study.datajpa.Dto.UsernameOnly;
import study.datajpa.Entity.Member;
import study.datajpa.Dto.UsernameOnlyDto;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

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

    //=======페이징=======
    @Query(value = "select m from Member m left join m.team t where m.age = :age", countQuery = "select count(m.id) from Member m where m.age = :age")
    Page<Member> findPagingByAge(@Param("age") int age, Pageable pageable);
    Slice<Member> findSliceByAge(int age, Pageable pageable);

    //=======벌크연산======
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    //=======fetch=======
    @Query("select m from Member m join fetch m.team t")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    //=======hint=======
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    //=======Lock=======
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);


    //=======projections=======
    List<UsernameOnly> findProjectionsInterfaceByUsername(@Param("username") String username);

    List<UsernameOnlyDto> findProjectionsDtoByUsername(@Param("username") String username);
    <T> List<T> findProjectionsDtoGenericByUsername(@Param("username") String username, Class<T> type);


    List<NestedClosedProjections> findNestedClosedProjectionsByUsername(@Param("username") String username);

    //=======aa=======
    //=======aa=======


}
