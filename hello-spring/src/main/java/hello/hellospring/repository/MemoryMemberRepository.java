package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository // Repository 어노테이션 : 스프링 실행 시 해당 Repository를 인식, 등록
public class MemoryMemberRepository implements MemberRepository {
    // Override 한꺼번에 하려면 alt + Enter 누르면 됨

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;
    // sequence는 key 값을 생성


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // Optional.ofNullable() 로 해당 값을 감싸면 값이 null이어도 클라이언트에서 작동 가능

    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // 넘어온 name이 동일한 지 확인
                .findAny(); // findAny() : 값이 동일한 것을 하나라도 찾는 메서드. 결과가 Optional로 나옴

    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear(); // store에 저장된 것을 모두 삭제
    }
}
