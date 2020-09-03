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

@Controller
public class MemberController {

    /* 회원 컨트롤러에 의존관계 추가 */
    private final MemberService memberService;

    /* 생성자 주입 방식 DI : 생성자를 통해서 memberService 주입*/
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    /* 필드 주입 방식 DI */
//    @Autowired private MemberService memberService;
//
//    /* setter 주입 방식 DI */
//    private MemberService memberService;
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

}
