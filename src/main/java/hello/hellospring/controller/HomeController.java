package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by frenchline707@gmail.com on 2020-08-20
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

@Controller
public class HomeController {

    /* 회원 웹 기능 - 홈 화면 추가 */
    @GetMapping("/")
    public String home() { //컨트롤러가 정적 파일(index.html)보다 우선순위가 높다
        return "home";
    }
}
