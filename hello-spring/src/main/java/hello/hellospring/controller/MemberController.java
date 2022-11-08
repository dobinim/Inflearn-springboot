package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    /* Controller 어노테이션 사용할 경우 스프링 실행 시 스프링 컨테이너가 생성되면서, 
    그 안에 자동으로 MemberController의 객체가 생성됨 -> 스프링이 관리 */

    // private final MemberService memberService = new MemberService();
    /* 이전엔 이렇게 직접 생성자를 이용해 필요한 객체를 써서 생성하는 방식을 취했으나,
       스프링이 관리를 하게 되면 전부 스프링 컨테이너에 등록 후 -> 거기서 받아서 쓰는 방식을 취해야 한다. WHY?
       - 예를 들어 다른 클래스나 컨트롤러에서 MemberService 객체를 사용하고자 할때, 
       실제로 해당 객체(클래스)에는 별 기능이 없으니 여러개 생성할 이유가 없는데도
       계속해서 새로 생성해서 써야하기 때문 (효율적이지 X)
        -> So, 스프링 컨테이너에 등록하고 (1번만 하면 됨) 같이 쓰면 됨! 어떻게?
        1. 우선 해당 클래스의 객체 선언
        2. 생성자 만들기
        3. Autowired 어노테이션 붙이기
        4. 해당 클래스에 Service 어노테이션 / 필요한 리포지터리에 Repository 어노테이션 붙이기 */
    private final MemberService memberService;


    // 생성자 만들기 -> alt + insert
    @Autowired
    /* Autowired 어노테이션 :
     - 스프링 실행 시 스프링 컨테이너에 등록된 해당 컨테이너가 실행되면서 그 안의 생성자로 인해
     자동적으로 해당 클래스 객체가 생성됨. */
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    /* 이 순서대로 했는데 영상에서는 생성자의 memberService에 오류가 있는 것으로 나오는데 나는 정상. WHY?
     -> Community 버전이라 해당 오류 지원하지 않아서 안나오는 것! 강의보고 그대로 따라할 것.
     - 무슨 오류인가?
     : memberService를 해당 스프링 빈으로 설정할 수 없다고 나옴 !
      -> 해결 방법 : 해당 클래스 객체에 각각 Service/Repository 어노테이션 붙여서 연결 => 의존성 주입(DI)! */



    /* 스프링 빈 등록하는 방법 2 가지
    1. 컴포넌트 스캔을 통해 자동적으로 의존관계 설정
    - @Controller, @Autowired, @Service, @Repository 어노테이션 => 모두 @Component 어노테이션을 포함
    - 원래는 @Component 어노테이션을 붙임
    - What if) 내가 java 폴더 아래에 따로 패키지 만들고 그 아래 클래스 생성해서 거기에 컴포넌트 스캔 이용하면 실행될까?
       -> 정답은 NO. WHY?
       - 스프링은 실행되는 해당 패키지와 그 하위 패키지 내에서만 컴포넌트 스캔을 실행한다!
       - 즉, 우리의 경우 hello.hellospring 패키지 내에서만 작동하는 것!
    2. 자바 코드로 직접 스프링 빈 등록 */

    /* 스프링에서 스프링 빈 등록 시 유의 사항
    - 스프링 컨테이너에 스프링 빈 등록 시, 기본적으로 "싱글톤" 으로 등록한다 -> 유일하게 1개만 등록한다는 소리! (2개 이상 X)
        - 해당되는 것들 모두 1개씩만 등록되기 때문에 사용할 때 모두 같은 인스턴스를 사용함.
        - 설정으로 싱글톤이 아니게 쓸 수 있지만, 특수한 케이스 제외하면 대부분 싱글톤!
    */
}
