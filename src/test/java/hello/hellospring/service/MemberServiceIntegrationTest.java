package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 회원 서비스 스프링 통합 테스트 : 스프링 컨테이너와 DB까지 연결한 통합 테스트 진행
 */

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다
@Transactional // 각 테스트는 독립적이며 반복이 가능해야 함 -> 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다 : 이렇게 해야 다음 테스트에 영향 주지 X
class MemberServiceIntegrationTest {

    //Test할 때는 필요한 것만 인젝션해서 쓰면 끝이므로, Field 기반으로 Autowired 받는 DI가 편하다
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    //@Commit /*이런 옵션을 주면 @Transactional 있어도, 테스트 수행 후 Roll back 되지 않고 그냥 Commit 됨*/
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}

/**
 * @SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다.
 * @Transactional : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스
 * 트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다
 * <p>
 * 데이터베이스의 '트랜잭션(Transaction)'
 * DB는 기본적으로 트랜잭션이란 개념이 있음
 * <p>
 * 그래서
 * DB에 데이터를 Insert쿼리를 날린 다음엔
 * 사실은 `Commit` 이란 걸 해줘야
 * DB에 반영이 됨
 * <p>
 * 그게 아니면
 * 보통 Auto commit 모드라고 하는데
 * 무조건 커밋이 들어가는 모드임
 * <p>
 * 보통 오토 커밋이냐, 오토 커밋이 아니냐의 차이
 * 자동으로 커밋을 하냐, 안하냐의 차이임
 * <p>
 * 기본적으로는 Transaction이라는 개념이 있어서
 * Insert 쿼리를 날린 다음에
 * 이거를 커밋하기 전까진 DB에 반영이 안된다
 * <p>
 * 그런데 테스트 끝내고 롤 백(Roll back)을 하면
 * DB에 테스트했던 데이터가 다 없어지고 반영이 안됨
 * <p>
 * 스프링에서는
 * @Transactional 이라는 애노테이션을
 * Test Case에 달면
 * 테스트를 실행할 때, 트랜잭션을 먼저 실행을 하고,
 * DB에 데이터를 Insert쿼리든 뭐든 다 날린 다음에
 * 테스트가 끝나면 롤 백(Roll back)을 해준다
 * <p>
 * 그래서 DB에 넣었던 데이터가
 * 다 깔끔하게 반영이 안되고 지워진다.
 */