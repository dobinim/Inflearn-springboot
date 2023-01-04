package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
// @Service // Service 어노테이션 : 스프링 실행 시 해당 어노테이션이 붙은 아이를 스프링 빈에 등록해줌
public class MemberService {
                // test 수행 시 따로 만들지 않고 클래스에 대고 ctrl + shift + t 누르면 됨.
    private final MemberRepository memberRepository;
        // private final MemberRepository memberRepository = new MemoryMemberRepository();
        // -> 2. 선언만 하고 외부에서 이용 가능하도록 아래와 같이 생성자를 만들어준다.
    // @Autowired
    /* 스프링 빈에 등록된 객체를 사용할 때마다 autowired 써서 연결
    -> MemberRepository를 구현한 MemoryMemberRepository의 객체가 자동적으로 주입됨 */
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /* 회원 가입 */
    public Long join(Member member){
        // 같은 이름이 있는 중복회원은 안된다고 가정
        validateDuplicateMember(member); // ctrl + alt + m 눌러서 해당 메서드 따로 선언해줌 (아래에 있음)
        memberRepository.save(member);
        return member.getId();

    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 회원 조회 */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> { // 이때 m은 member를 가리킴
                throw new IllegalStateException("이미 존재하는 회원입니다.");
             });
            // ifPresent() : 만약 얘가 값이 이미 존재하는 애라면 (확인하는 메서드)
            /* Optional<Member> result = memberRepository.findByName(member.getName());
            -> memberRepository.findByName(member.getName()).ifPresent...
            - Optional 을 바로 쓰는 게 모양이 예쁘지 않으므로,
            memberRepository.findByName(member.getName()) 뒤에 바로 .ifPresent 사용. 왜?
            -> 이미 Optional 형태로 반환되기 때문. */
    }
}
