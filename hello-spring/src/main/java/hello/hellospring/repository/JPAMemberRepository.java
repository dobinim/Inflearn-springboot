package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JPAMemberRepository implements MemberRepository {

    private final EntityManager em;
    // JPA를 쓰려면 얘 있어야함 -> JPA는 EntityManager로 모든 것이 동작함
    // build.gradle에 입력해서 받아 온 data-jpa 라이브러리를 통해 스프링부트가 자동으로
    // 현재의 데이터베이스들을 이용할 수 있는 EntityManager를 생성

    public JPAMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        // em.find(조회할타입, 식별자); : 이때 식별자는 PK여야 함.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        // ctrl + alt + n 누르면 단축됨
        return result;
    }
}
