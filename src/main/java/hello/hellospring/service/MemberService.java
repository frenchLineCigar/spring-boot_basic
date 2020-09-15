package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* 회원 도메인의 비즈니스 로직을 작성하는 계층 */
@Transactional /* JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다 */
public class MemberService {

    /* 회원 서비스에 의존관계 추가 */
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) { //생성자 주입 방식 DI
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입 : 회원 객체를 받아 저장하고, 회원 id를 반환
     */
    public Long join(Member member) {


        /* 회원 가입 시간, 회원 조회 시간을 측정하고 싶다면? 더 나아가 모든 메소드의 호출 시간을 측정하고 싶다면? (AOP를 사용하지 않을 경우) */
        
        /* 메소드 호출 시간 측정 */
        long start = System.currentTimeMillis();

        try {
            //같은 이름이 있는 중복 회원 X (이름 -> 닉네임이라고 생각하자 )
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMS = finish - start;
            System.out.println("join = " + timeMS + "ms");
        }

    }

    /* 중복 회원 검증 */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) //findByName()의 리턴 타입은 Optional<Member>
                .ifPresent(m -> { //findByName() 리턴 값에 어떤 값(m)이 있으면(중복이 있으면), { 함수 } 가 동작한다
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {

        /* 메소드 호출 시간 측정 */
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = " + timeMs + "ms");
        }

    }
    /**
     * [ 위와 같이 시간 측정 코드를 작성할 경우 문제 ]
     * 회원가입, 회원 조회에 시간을 측정하는 기능은 `핵심 관심 사항`이 아니다.
     * 시간을 측정하는 로직은 `공통 관심 사항`이다.
     * 시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 `유지보수`가 어렵다.
     * 시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다.
     * 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.
     * 만약 메서드가 100개가 넘을 경우 일일히 노가다 할 것인가? `그거 AOP 쓰면 되요.`
     *
     * [ AOP가 필요한 상황이라 인식 ]
     * 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern)의 분리가 필요하다
     * 회원 가입 시간, 회원 조회 시간을 측정하고 싶다 : 공통 관심 사항
     */

    /**
     * ID로 특정 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
