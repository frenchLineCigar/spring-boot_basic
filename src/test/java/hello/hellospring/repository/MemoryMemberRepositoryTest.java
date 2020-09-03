package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* 회원 레포지토리 구현체(MemoryMemberRepository) 테스트 케이스 작성 */
class MemoryMemberRepositoryTest { //access modifier를 굳이 public으로 하지 않았다(default가 기본 지정자) -> 다른 곳에서 가져다 쓸 용도가 아니므로

    MemoryMemberRepository repository = new MemoryMemberRepository();

    /* Test가 끝낼 때마다 Repository를 깔끔하게 지운다 */
    @AfterEach //각 테스트 메서드 실행이 끝날 때마다 어떤 동작을 하게끔 만드는 애노테이션 (콜백 메서드라고 보면 됨)
    public void afterEach() {
        repository.clearStore();
        // 테스트 순서는 보장이 안된다. 모든 테스트는 순서과 상관없이 메서드 별로 따로 동작하게 설계를 해야된다
        // 테스트는 서로 순서와 관계없이, 서로 의존관계 없이 설계가 되어야 함!
        // 그러기 위해선 하나의 테스트가 끝날 때마다 저장소나 공용 데이터 (변수) 들을 다시 깔끔하게 지워줘야 문제가 없다
    }

    @Test
    public void save() { /* 회원 등록 테스트 */
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        //데이터가 잘 저장되었는지 검증 : 위의 샘플 객체(member)와 DB 조회한 결과(result)와 똑같은지 확인한다
        Member result = repository.findById(member.getId()).get();

        /* assertj 사용 */
        assertThat(member).isEqualTo(result); //import static org.assertj.core.api.Assertions.*;

        /* 단순한 방법 */
//        System.out.println("result = " + (result == member));

        /* junit 사용 */
//        assertEquals(Expected, Actual) : 첫번째 파라미터에 기대하는 값, 두번째 파라미터에 실제 값
//        Assertions.assertEquals(member, result); //녹색불
//        Assertions.assertEquals(member, null); //빨간불 

    }

    @Test
    public void findByName() { /* 이름으로 회원 찾기 테스트 */
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
//        Member result = repository.findByName("spring2").get();
//        Member result = repository.findByName("spring3").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll() { /* 전체 회원 조회 테스트 */

        // 3개의 샘플 회원을 저장한 뒤, findAll()로 조회한 데이터가 3개인지 확인
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member member3 = new Member();
        member3.setName("spring3");
        repository.save(member3);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(3); //녹색불
//        assertThat(result.size()).isEqualTo(4); //빨간불

    }

}
