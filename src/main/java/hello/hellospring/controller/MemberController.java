package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* 회원 컨트롤러가 회원서비스와 회원 리포지토리를 사용할 수 있게 의존관계 설정해본다 */

@Controller
public class MemberController {

    /* 회원 컨트롤러에 의존관계 추가 */
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 생성자에 @Autowired 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다. 이렇게
     * 객체 의존관계를 외부에서 넣어주는 것을 DI (Dependency Injection), 의존성 주입이라 한다.
     *
     * 생성자에 @Autowired 를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아서
     * 주입한다. 생성자가 1개만 있으면 @Autowired 는 생략할 수 있다.
     */

    /**
     * 컴포넌트 스캔 원리
     * @Component 애노테이션이 있으면 스프링 빈으로 자동 등록된다.
     * @Controller 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
     * @Component 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다.
     * > @Controller
     * > @Service
     * > @Repository
     */

}
