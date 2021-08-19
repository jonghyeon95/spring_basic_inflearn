package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//        basePackages = "hello.core.member",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //Configuration 제외하고 스캔하겠다
)
public class AutoAppConfig {

//    @Bean("memoryMemberRepository")
//    MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }

}
