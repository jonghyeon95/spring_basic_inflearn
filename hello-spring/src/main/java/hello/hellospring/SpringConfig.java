package hello.hellospring;

import hello.hellospring.aop.TimeTraceApp;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

//직접 등록하는법
@Configuration
public class SpringConfig {

    private DataSource dataSource;
    private EntityManager em;
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em, MemberRepository memberRepository) {
        this.dataSource = dataSource;
        this.em = em;
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public TimeTraceApp timeTraceApp(){
//        return new TimeTraceApp();
//    }

//    @Bean
//    public MemberRepository memberRepository(){
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSource);
////        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//
//    }
}
