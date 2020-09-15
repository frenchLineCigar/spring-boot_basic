package hello.hellospring.domain;

/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 비즈니스 요구사항
 * > 데이터: 회원ID(식별자), 이름
 * > 기능: 회원 등록, 조회
 */

/* 회원 도메인 객체 */
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //ID 식별자 -> 고객이 정하는 값이 아닌, 데이터 구분을 위해서 시스템이 저장하는 값
    private String name; //이름

//자바에서는 private을 사용해서 멤버 변수로의 접근을 제한합니다.
//Private 으로 선언된 인스턴스 필드에 접근하기 위해서 Getter와 Setter를 사용합니다

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
