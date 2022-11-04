package hello.hellospring.service;
        // 클래스에서 직접 테스트 생성 시 알아서 test 아래에 똑같은 패키지 아래 생성됨 :)
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // 이 전체를 돌렸을 때 에러가 발생하지 않기 위해 메소드 실행 시마다 데이터 클리어되도록 하자. -> AfterEach
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    // MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // 이렇게 생성했을 때의 단점 -> 같은 인스턴스를 사용하게 해야하는데, 지금 같은 경우는 각기 다른 인스턴스를 사용하고 있음.
    // 수정하는 방법 -> 1. 우선 memberService로 이동
    // 3. 돌아와서 MemoryMemberRepository도 선언만 해준 뒤 beforeEach 생성


    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        // MemoryMemberRepository 객체를 먼저 생성해준 뒤 걔를 MemberService에 넣어준다
        // -> 그럼 같은 MemoryMemberRepository를 사용하게 됨.
        // MemberService에서 직접 생성하지 않고 외부에서 생성된 것을 받아와서 쓰는 형태가 됨 = 의존성 주입(DI)
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }


    @Test
    void 회원가입() { // 원래는 join() : 테스트 시 메소드 이름은 한글로 해도 OK -> 빌드될 때는 실제 코드에 포함되지 X
        // given, when, then 용법을 사용하라
        // given : 어떤 상황이 주어졌을 때 (기반이 되는 데이터)
        Member member = new Member();
        member.setName("hello");

        // when : 이걸 실행하면 (테스트하는 부분)
        Long saveId = memberService.join(member);

        // then : 이런 결과가 나와야 해 (검증되는 부분)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    /* But, 테스트에서 중요한 것은 정상 로직보다 "예외" 다!
       우리는 중복회원 검증이 제대로 되는지 알아보고 싶으니 이를 테스트해보자. */

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        /* 예외 처리
        try {
            memberService.join(member2);
            fail(); // 여기까지 오면 실패
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        } */
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // () -> memberService.join(member2)가 실행되었을 때 IllegalStateException 예외가 터져야 된다!
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}