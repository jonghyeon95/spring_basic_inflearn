package jpabasic.ex1hellojpql;

import org.aspectj.weaver.ast.Or;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
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

			List<String> resultList = em.createQuery("select coalesce(m.username, '이름없는 회원') from Member m"
					, String.class).getResultList();

			for (String s : resultList) {
				System.out.println("s = " + s);
			}

			List<String> resultList2 = em.createQuery("select nullif(m.username, 'member1') from Member m"
					, String.class).getResultList();

			for (String s : resultList2) {
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
