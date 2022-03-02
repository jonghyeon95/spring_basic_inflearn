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

            Member member = Member.builder().username("member1").homeAddress(Address.builder().city("homeCity").zipcode("1000").street("strret").build())
                    .build();

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

//            member.getAddressHistory().add(AddressEntity.builder().address(Address.builder().city("old1").zipcode("1000").street("stret").build()).build());
//            member.getAddressHistory().add(AddressEntity.builder().address(Address.builder().city("old2").zipcode("1000").street("street").build()).build());

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===================");

            Member findMember = em.find(Member.class, member.getId());
            findMember.getUsername();

//            //member의 주소변경
//            findMember.setHomeAddress(Address.builder().city("newCity").zipcode("1000").street("strret").build());
//
//            //치킨 -> 한식으로 변경
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().remove("한식");

//            //주소히스토리 변경
//            findMember.getAddressHistory().remove(Address.builder().city("old1").zipcode("1000").street("strret").build());
//            findMember.getAddressHistory().add(Address.builder().city("newCity1").zipcode("1000").street("strret").build());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }


        em.close();
        emf.close();
    }

}
