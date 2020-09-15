package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by frenchline707@gmail.com on 2020-09-02
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* Spring Data JPA 회원 리포지토리 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository { //interface 다중 상속

    @Override
    Optional<Member> findByName(String name);  //JPQL : select m from Member m where m.name = ?

    /**
     * [ Spring Data JPA 제공 기능 ]
     * - 인터페이스를 통한 기본적인 CRUD (JpaRepository < PagingAndSortingRepository < CrudRepository < Repository )
     * - findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공
     * - 페이징 기능 자동 제공
     */

    //And, Or example
//    Optional<Member> findByNameAndId(String name, Long id);
//    Optional<Member> findByNameOrId(String name, Long id);
}
