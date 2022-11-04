package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 스프링은 컨트롤러를 우선 어노테이션으로 컨트롤러라고 설정해줘야 한다.
public class HelloController {

    @GetMapping("hello") // 맵핑 - RequestMapping이랑 같은 것인듯?
    public String hello(Model model){
       model.addAttribute("data", "hello!!");
       return "hello";

    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // HTML의 Body 부분에 내가 이 데이터를 직접 전송하겠다. (요청한 클라이언트에 직접 데이터 전송)
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        // Ctrl + Shift + Enter 치면 (); 를 알아서 작성해주고 들여쓰기도 정리해줌
        hello.setName(name);
        return hello;

    }

    static class Hello {
        private String name;

        
        // Java Bean 규약에 의한 Getter, Setter 선언 (Java Bean 표준 방식 / Property 접근 방식이라고도 함)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
