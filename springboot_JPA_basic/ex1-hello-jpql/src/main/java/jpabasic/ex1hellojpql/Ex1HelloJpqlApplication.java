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

			Member member1 = Member.builder().username("member1").age(10).type(MemberType.ADMIN).build();
			member1.changeTeam(teamA);
			em.persist(member1);

			Member member2 = Member.builder().username("member2").age(11).type(MemberType.ADMIN).build();
			member2.changeTeam(teamA);
			em.persist(member2);

			Member member3 = Member.builder().username("member3").age(12).type(MemberType.ADMIN).build();
			member3.changeTeam(teamB);
			em.persist(member3);

			em.flush();
			em.clear();

			System.out.println(" ============================= ");

//			List<Member> resultList = em.createQuery("select m from Member m join fetch m.team"
//					, Member.class).getResultList();
//
//			for (Member member : resultList) {
//				System.out.println("member = " + member);
//				System.out.println("team = " + member.getTeam());
//			}

			List<Team> resultList = em.createQuery("select t from Team t", Team.class)
					.setFirstResult(0)
					.setMaxResults(2)
					.getResultList();
			for (Team team : resultList) {
				System.out.println("team = " + team);
				System.out.println("team.getMembers().size() = " + team.getMembers().size());
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
