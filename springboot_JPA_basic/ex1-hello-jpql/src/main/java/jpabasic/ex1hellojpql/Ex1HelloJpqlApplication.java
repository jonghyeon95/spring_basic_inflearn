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

			for (int i = 0; i < 100; i++) {
				Member member = Member.builder().username("member1").age(i).build();
				em.persist(member);
			}


			em.flush();
			em.clear();

			List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
					.setFirstResult(2)	//인덱스번호
					.setMaxResults(5)	//출력갯수
					.getResultList();

			for (Member member1 : resultList) {
				System.out.println("member1 = " + member1);
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
