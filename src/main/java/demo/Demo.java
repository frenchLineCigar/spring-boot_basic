package demo;

import org.springframework.stereotype.Service;

@Service //스프링 빈으로 등록되지 않음
public class Demo {
}

/*
 * HelloSpringApplication.java가 위치한 패키지 hello.hellospring.* 하위들은 스프링이 뒤져서 빈으로 등록
 * 패키지가 동일하거나 하위 패키지만 등록
 * 그렇지 않은 위치는 스프링 빈으로 컴포넌트 스캔 하지 않음
 * 등록하려면 기타 설정이 필요함(컴포넌트 스캔 범위 지정)
 * */