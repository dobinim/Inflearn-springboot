package hello.hellospring.service;
        // 클래스에서 직접 테스트 생성 시 알아서 test 아래에 똑같은 패키지 아래 생성됨 :)

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
// Transactional 어노테이션을 테스트에 적용할 경우 :
//    테스트 적용 시 transactional을 우선 실행 -> 테스트 실행 후 바로 rollback을 해준다.
class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    // 테스트할땐 그냥 Autowired로 (따로 생성자 없이) 처리하는 게 더 간결함
    // SpringConfig 에 설정해 둔 데서 올것임!


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

        
    }


}