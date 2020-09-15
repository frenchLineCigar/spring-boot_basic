package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by frenchline707@gmail.com on 2020-09-02
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * [ AOP 적용 ] 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리
 * - AOP: Aspect Oriented Programming
 * - 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리
 */
@Aspect
@Component
public class TimeTraceAop { //시간 측정 로직 (공통 관심 사항)

    @Around("execution(* hello.hellospring..*(..))")  //-> 원하는 곳에 공통 관심 사항 적용
    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() +" " + timeMs + "ms");
        }
    }

    /**
     * 시간을 측정하는 로직을 별도의 공통 로직으로 만듦 (병목 체크 용도)
     * 핵심 관심 사항을 깔끔하게 유지 가능
     * 변경이 필요하면 이 로직만 변경하면 된다
     * 원하는 적용 대상을 선택할 수 있다 : @Around("execution(* hello.hellospring.service..*(..))")
     *
     * AOP 적용 후 의존관계 (프록시 방식의 AOP)
     * : helloController -> `프록시 memberService` -> joinPoint.proceed() -> 실제 memberService
     */
}
