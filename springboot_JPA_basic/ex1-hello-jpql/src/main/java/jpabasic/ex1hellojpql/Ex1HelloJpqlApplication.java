package jpabasic.ex1hellojpql;

import org.aspectj.weaver.ast.Or;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class Ex1HelloJpqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex1HelloJpqlApplication.class, args);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			Team team = Team.builder().name("teamA").build();
			em.persist(team);

			Member member = Member.builder().username("member1").age(10).type(MemberType.ADMIN).build();
			member.changeTeam(team);
			em.persist(member);

			Member member1 = Member.builder().age(10).type(MemberType.ADMIN).build();
			member.changeTeam(team);
			em.persist(member1);

			em.flush();
			em.clear();

			List<String> resultList = em.createQuery("select m.username from Member m"	//상태필드 (더이상 탐색X)
					, String.class).getResultList();

			em.createQuery("select m.team from Member m", Team.class);	//단일 값 연관 경로, 묵시적 내부 조인 발생 (뒤에서 조인이 일어남). 탐색이 가능 m.team.aaa

			em.createQuery("select t.members from Team t", Collections.class);	//컬렉션 값 연관 경로, 묵시적 내부 조인 발생. 탐색X tm.members.안됨

			em.createQuery("select m from Team t join Member m", Member.class);	//컬렉션 값 연관 경로, 명시적 조인을 통해 탐색 가능

			for (String s : resultList) {
				System.out.println("s = " + s);
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		em.close();
		emf.close();
	}

}
