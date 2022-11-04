package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    // Optional : java 8 에서 추가된 기능으로, null 값이 나올 경우 바로 null이 아니라 optional 로 감싸서 반환
    Optional<Member> findByName(String name);
    List<Member> findAll(); // 모든 회원의 리스트를 보여주는 것



}
