package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Created by frenchline707@gmail.com on 2020-09-01
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) { /* EntityManager 주입*/
        this.em = em;
    }

    /* 회원 저장 */
    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    /* 식별자(pk)로 회원 조회 */
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);// find -> Type(EntityClass)과 식별자(PK) 넣어준다
        return Optional.ofNullable(member); //ofNullable -> 값이 null일 수 있으므로
    }

    /* name으로 회원 조회 : pk 기반이 아닌 조회는 JPQL(객체지향쿼리언어)을 작성 */
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        //findByName을 1개만 찾는다고 했으므로 스트림을 이용해서
        return result.stream().findAny();
    }

    /* 전체회원 조회 : JPQL(객체지향쿼리언어) */
    @Override
    public List<Member> findAll() {
        //JPQL : 객체지향쿼리언어 -> 테이블(member) 대상으로 작성해 날리던 쿼리를, Entity(Member)를 대상으로 쿼리를 날림 -> 이게 SQL로 변환이 됨
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

}

/**
 * [ IntelliJ Tip ] 코드 리팩토링
 * Inline Variable 단축키:
 * Ctrl + Alt + N
 *
 * Refactor This 단축키:
 * Ctrl + Shift + Alt + T -> 이후 inline 검색하면 나옴
 */