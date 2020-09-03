package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* 회원 객체를 처리하는 저장소 인터페이스 */
public interface MemberRepository {
    Member save(Member member); //회원 저장 : 회원을 저장하면 저장된 회원이 반환되도록

    Optional<Member> findById(Long id); //아이디로 회원 찾기

    Optional<Member> findByName(String name); //이름으로 회원 찾기

    List<Member> findAll(); //전체 회원 조회

    /**
     * Java 8의 Optional 기능 : findById, findByName으로 조회했을 때 값이 null일 수 있다.
     * 요즘에는 null을 처리하는 방법에서 null을 그대로 반환하는 방법 대신
     * Optional 이라는 객체로 감싸서 반환하는 방법을 많이 선호한다.
     * Java 8에 소개된 기능
     */

}
