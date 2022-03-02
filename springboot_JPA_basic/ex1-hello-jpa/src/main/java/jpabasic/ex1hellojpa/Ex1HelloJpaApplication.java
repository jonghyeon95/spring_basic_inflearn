package jpabasic.ex1hellojpa;

import org.aspectj.weaver.ast.Or;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Ex1HelloJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ex1HelloJpaApplication.class, args);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = Member.builder().address(Address.builder().city("안양").street("11번길").zipcode("1234").build())
                    .period(Period.builder().build()).build();
            Order order = Order.builder().build();

            member.addOrder(order);

            em.persist(member);
            em.persist(order);


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }


        em.close();
        emf.close();
    }

}
