package jpabasic.ex1hellojpql;

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

//			TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//			Query query3 = em.createQuery("select m.username, m.id from Member m");	//타입정보가 미확실할때는 Query 사용
			TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.username = :username", Member.class);
			query1.setParameter("username", "member1");


//			List<Member> resultList = query1.getResultList();	//비어있을때는 빈리스트 반환, null이 아니다
			Member singleResult = query1.getSingleResult();	//결과가 없거나, 둘 이상이면 Exception
			System.out.println("singleResult = " + singleResult);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		em.close();
		emf.close();
	}

}
