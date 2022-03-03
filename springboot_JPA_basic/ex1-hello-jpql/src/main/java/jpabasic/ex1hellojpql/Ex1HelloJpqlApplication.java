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

			Member member = Member.builder().username("member1").age(1).build();
			em.persist(member);

			em.flush();
			em.clear();

//			List<Object[]> resultList = em.createQuery("select m.age, m.username from Member m").getResultList();
//			System.out.println("result = " + resultList.get(0)[0] + ", " + resultList.get(0)[1]);

			List<MemberDto> resultList1 = em.createQuery("select new jpabasic.ex1hellojpql.MemberDto(m.username, m.age) from Member m", MemberDto.class).getResultList();
			System.out.println("resultList1 = " + resultList1);


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		em.close();
		emf.close();
	}

}
