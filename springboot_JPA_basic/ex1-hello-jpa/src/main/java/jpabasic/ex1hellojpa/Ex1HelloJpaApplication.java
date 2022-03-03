package jpabasic.ex1hellojpa;

import org.aspectj.weaver.ast.Or;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

            //일반 jpql
            List<Member> resultList = em.createQuery("select m from Member m where m.username like '%kim%'", Member.class).getResultList();

            //NativeQuery
            List<Member> resultList2 = em.createNativeQuery("select * from member", Member.class).getResultList();

            //동적쿼리 위한 criteria
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m);

            String username = "aa";
            if (username != null) {
                cq = cq.where(cb.equal(m.get("username"), "kim"));
            }
            List<Member> resultList3 = em.createQuery(cq).getResultList();

            //동적쿼리 위한 QueryDSL


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }


        em.close();
        emf.close();
    }

}
