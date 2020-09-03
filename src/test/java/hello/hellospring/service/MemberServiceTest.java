package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* 회원 서비스 테스트 케이스 */
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    /* 각 Test 실행 전에 동작하도록 */
    @BeforeEach //각 테스트 메서드 실행 전마다 어떤 동작을 하게끔 만드는 애노테이션
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    /* 각 Test가 끝낼 때마다 동작하도록 */
    @AfterEach //각 테스트 메서드 실행이 끝날 때마다 어떤 동작을 하게끔 만드는 애노테이션 (콜백 메서드라고 보면 됨)
    public void afterEach() {
        memberRepository.clearStore(); //Repository를 비운다
    }

    @Test
    void 회원가입() {
        //given : 무언가가 주어졌는데 (이 데이터를 기반으로)
        Member member = new Member();
        member.setName("spring");

        //when : 이것을 실행했을 때 (이걸 검증)
        Long saveId = memberService.join(member); //저장한 id를 리턴

        //then : 결과가 이게 나와야 돼 (검증부)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    /* 테스트는 정상 플로우도 중요한데, 예외 플로우가 훨씬 더 중요함 */
    @Test
    public void 중복_회원_예외() { //중복 회원 검증 로직을 잘 타서 예외를 터트리는지 확인
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //IllegalStateException e = assertThrows(NullPointerException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다22.");

/*
        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail("예외가 발생해야 합니다."); //예외를 못잡았으니 실패
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
            //assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123");
        }
*/

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}