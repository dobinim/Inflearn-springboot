package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // 실행 시 맨 처음 매핑되는 도메인
    public String home() {
        return "home";
    }

    // 기존에 설정한 Welcome page가 있더라도, 컨트롤러에서 실행 시 먼저 이쪽으로 가게 해놓으면
    // 해당 페이지로 가지 않고 이렇게 설정한 페이지로 간다. 왜?
    // 요청을 받으면 스프링 컨테이너부터 뒤져서 이쪽에 우선순위를 두기 때문!
}
