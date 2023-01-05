package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 인터페이스가 인터페이스를 받을 땐 implements 가 아니라 extends!
// 인터페이스는 다중상속 가능
public interface SpringDataJPAMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
    // 이렇게 치면 JPQL이 : SELECT m FROM Member m WHERE m.name = ? 로 번역해서 실행
    // findBy~~ -> 뒤에 찾고 싶은 애들을 적고, 매개변수를 적으면 됨 :)   예) findByNameAndId(String name, Long id)
    // 즉, 메서드 이름만으로 기능 제공이 가능!
}
