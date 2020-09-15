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

        /* AOP 적용 후, 실제 Proxy가 주입되는지 콘솔에 출력해서 확인 */
        System.out.println("memberService = " + memberService.getClass());

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

    /* 회원 등록 폼 */
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    /* 회원을 실제 등록하는 기능 */
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/"; //등록후 홈화면으로 리다이렉트
        //return "redirect:/members";
    }

    /* 회원 조회 기능 */
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); //members를 model에 담아서 화면에 넘김
        return "members/memberList";
    }

}
