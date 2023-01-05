package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Configuration 어노테이션 : 얘는 설정하는 데 쓰인단 소리
public class SpringConfig {

//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    /* Bean 어노테이션 : 얘를 스프링 빈으로 등록
    - Bean 어노테이션을 붙인 메서드를 다음과 같이 설정하면 스프링은 '이 로직을 실행하여 빈으로 등록하란 소리구나' 하고
      로직을 실행해 MemberService 객체를 빈으로 등록한다. */
    public MemberService memberService() {
        return new MemberService(memberRepository);
        // 그냥 return new MemberService() 하면 에러. 왜?
        // MemberService는 MemberRepository를 받았으니까 걔도 등록한 뒤 생성자 안에 넣어줘야 함.
    }

//    @Bean
//    public MemberRepository memberRepository() {
        // return new JdbcTemplateMemberRepository(dataSource);
        // return new MemoryMemberRepository();
        // 왜 MemoryMemberRepository? -> MemberRepository 인터페이스를 구현한 구현체가 MemoryMemberRepository 니까!
        // return new JdbcMemberRepository(dataSource); // 순수 JDBC용 레포지토리
        // return new JPAMemberRepository(em);
//    }

    // 순수 JDBC 사용 시 설정하기
    // 1. 우선, MemberRepository의 구현체인 JdbcMemberRepository로 Bean 설정

}
