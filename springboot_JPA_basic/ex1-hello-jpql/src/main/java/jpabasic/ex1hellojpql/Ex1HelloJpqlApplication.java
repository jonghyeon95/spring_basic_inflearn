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

			Team teamA = Team.builder().name("teamA").build();
			em.persist(teamA);

			Team teamB = Team.builder().name("teamB").build();
			em.persist(teamB);

			Member member1 = Member.builder().username("member1").age(15).type(MemberType.ADMIN).build();
			member1.changeTeam(teamA);
			em.persist(member1);

			Member member2 = Member.builder().username("member2").age(20).type(MemberType.ADMIN).build();
			member2.changeTeam(teamA);
			em.persist(member2);

			Member member3 = Member.builder().username("member3").age(25).type(MemberType.ADMIN).build();
			member3.changeTeam(teamB);
			em.persist(member3);

			em.flush();
			em.clear();

			System.out.println(" ============================= ");

			int result = em.createQuery("update Member m set m.age = 21 where m.age < 23").executeUpdate();
			System.out.println("result = " + result);

			em.clear();

			List<Member> findMemberList = em.createQuery("select m from Member m", Member.class).getResultList();
			for (Member member : findMemberList) {
				System.out.println("member = " + member);
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
