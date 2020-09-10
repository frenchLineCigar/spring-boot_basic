package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by frenchline707@gmail.com on 2020-08-20
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

@Configuration
public class SpringConfig { //스프링 설정 (스프링 컨테이너에 올리고 조립할 녀석들)

    /*
    향후 메모리 리포지토리를 다른 리포지토리로 변경할 예정이므로, 컴포넌트 스캔 방식 대신에 자바 코드로 스프링 빈을 설정함
    실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다.
    그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.
    */

    /* DataSource 주입 : 데이터베이스 커넥션을 획득할 때 사용하는 객체. */
    private final DataSource dataSource; //스프링 부트는 데이터베이스 커넥션 정보(application.properties 내 DB 설정)를 바탕으로 DataSource를 생성하고 스프링 빈으로 만들어둔다. 그래서 DI를 받을 수 있다

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /* 스프링 애플리케이션이 구동될 때, @Bean이 있는 객체들을 스프링 빈으로 등록해서 컨테이너에 등록해 둠 */
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() { //인터페이스 확장
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource); //구현체 바꿔치기
    }
    /**
     * 스프링의 DI (Dependencies Injection)을 사용하면 기존 코드를 전혀 손대지 않고, 설정만으로 구현 클래스를 변경할 수 있다.
     * -> 이런 대목이 Spring Bean 관리로 설계 시 장점
     * 객체 지향적 설계시 다형성(Polymorphism) 개념의 활용 : Interface를 두고 구현체 바꿔끼기 (기능을 변경해도, 조립코드 외엔, 앱 전체를 수정할 필요 X)
     * -> 스프링은 이런 것을 굉장히 편리하게 되도록, 스프링 컨테이너가 이를 지원해준다
     * -> 소위 말하는 DI 덕분에 굉장히 편리해짐 : 기존 코드 전혀 손대지 X, 설정만으로 구현 클래스를 변경
     * 기존의 코드는 하나도 손대지 않고, 오직 애플리케이션을 설정하는 코드(이를 어셈블리 라고 함. 조립한다고...)
     * 이런 애플리케이션 조립하는 코드부만 손대면, 나머지 실제 어플리케이션에 관련된 코드는 하나도 손댈 게 없는 것이다!
     * 개방-폐쇄 원칙(OCP, Open-Closed Principle)이 지켜짐 : 확장에는 열려있고, 수정/변경에는 닫혀있다.
     * 이것을 굉장히 편리하게 해주는 것이 스프링의 장점!
     */


}

