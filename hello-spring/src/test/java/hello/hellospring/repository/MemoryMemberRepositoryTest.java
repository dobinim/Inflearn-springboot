package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    // 테스트하고자 하는 클래스의 이름을 따와서 뒤에 Test를 붙이는 것이 관례
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 한 메서드가 끝날때마다 어떤 동작을 수행하도록 하는 어노테이션 (콜백 메서드)
    public void afterEach() {
        repository.clearStore(); // 각 메서드의 테스트가 수행될 때마다 사용한 데이터 비워줌
    }

    @Test // 이렇게 하면 얘를 테스트 실행할 수 있음.
    public void save(){
        Member member = new Member();
        member.setName("spring"); // 중간에 치고 다음 칸으로 내려갈 때 -> ctrl + shift + enter

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        // findById는 optional을 반환한다 -> Optional은 get()으로 가져옴
        System.out.println("result = " + (result == member));
        // 가져온 값과 DB의 값이 같으면 참이 나옴 -> true 가 나올 것임. 근데 이건 글자로만 나옴
        assertThat(member).isEqualTo(result); // member가 result랑 똑같은가?
        // 원래는 Assertions.assertThat... 인데 Assertions를 alt + enter로 static으로 넣으면 생략 가능함

    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); // 위에꺼 그대로 복사해다 붙였을때 이름에 대고 shift + f6 누르면 renaming 가능. 그때 이름 바꾸면 아래도 전부 바뀜
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2); // result의 크기가 2개인가?

    }

    // 이렇게 개별적으로 test를 하면 잘 작동되는데 아예 이 클래스를 실행해보면? -> 에러가 발생함. why?
    // 내가 위에서부터 쳤다고 해서 그 순서 그대로 작동되지 않음!
    // 모든 테스트는 순서와 관계없이 각자 작동하게끔 구현해야 함.
    // findAll() 에서 이미 spring1, spring2 가 저장되어있는데 findByName()에서 또 생성하려고 하니 에러나는 것.
    // 즉, 모든 메서드가 실행될때 데이터가 클리어되도록 해야함.
    // MemoryMemberRepository 에 데이터 클리어되는 메서드 작성 후 이 테스트 클래스에 afterEach 사용해주기
}
