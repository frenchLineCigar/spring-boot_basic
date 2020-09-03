package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* 회원 도메인의 비즈니스 로직을 작성하는 계층 */
@Service /* 회원 서비스 스프링 빈 등록 */
public class MemberService {

    /* 회원 서비스에 의존관계 추가 */
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) { //DI
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입 : 회원 객체를 받아 저장하고, 회원 id를 반환
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원 X (이름 -> 닉네임이라고 생각하자 )
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
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
        return memberRepository.findAll();
    }

    /**
     * ID로 특정 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
