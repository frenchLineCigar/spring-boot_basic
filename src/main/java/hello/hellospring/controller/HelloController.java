package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by frenchline707@gmail.com on 2020-08-18
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "spring!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
//    @GetMapping("hello-mvc2")
//    public String helloMvc2(@RequestParam(value = "key", required = false) String name, Model model) {
//        model.addAttribute("key", "name");
//        return "hello-template2";
//    }

    /**
     * Chp02 스프링 웹 개발 기초 > API ( @ResponseBody를 통한 API 방식 )
     *
     * @ResponseBody 사용 원리
     * @ResponseBody 를 사용
     * - HTTP의 BODY에 문자 내용을 직접 반환
     * - viewResolver 대신에 HttpMessageConverter 가 동작
     * - 기본 문자처리: StringHttpMessageConverter
     * - 기본 객체처리: MappingJackson2HttpMessageConverter
     * - byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
     * <p>
     * MappingJackson2HttpMessageConverter
     * <p>
     * 객체를 JSON으로 바꿔주는 유명한 변환 라이브러리
     * 대표적인 2가지 (범용성, 검증됨)
     * <p>
     * 1. Jackson2 - 스프링이 기본으로 넣어놓은 라이브러리
     * 2. 구글의 GSON
     * <p>
     * > 참고: 클라이언트의 HTTP Accept 헤더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서
     * HttpMessageConverter 가 선택된다. 더 자세한 내용은 스프링 MVC 강의에서 설명.
     */

    /* 문자 반환 : HttpMessageConverter -> StringHttpMessageConverter */
    @GetMapping("hello-string")
    @ResponseBody //http 통신 프로토콜에서 header부와 body부분이 있다. 응답하는 body부에 이 내용을 직접 반환해 넣어주겠다
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //hello spring
        /**
         * '@ReponseBody에 의해서 "hello spring" 문자를 요청한 클라이언트에 그대로 반환한다. 그대로 http 응답에 넘기고 끝'
         * HttpMessageConverter(StringConvertor)
         * 템플릿 엔진과 차이 : VIEW가 없이, 문자열 그대로 내려줌
         */
    }

    /* 객체 반환 : HttpMessageConverter -> MappingJackson2HttpMessageConverter */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
//        Hello hello = new Hello();
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
        /**
         * return hello; -> '@ResponseBody + `객체`(hello)를 넘김 -> JSON 포멧으로 변환해서 반환'
         * HttpMessageConverter(JsonConvertor) -> http 응답에 객체를 넘기는 경우 JSON방식으로 데이터를 만들어서 반환하는 것이 기본임
         */
    }

    /* static member class(정적 멤버 클래스) : https://siyoon210.tistory.com/141  */
    static class Hello { // 내부에 static 클래스로 만들면 외부 클래스 안에서 이 클래스를 또 쓸 수 있다
        private String name; // private 이므로 외부에서 바로 프로퍼티에 접근 못함

        //getter & setter -> 프로퍼티 접근 방식 -> java bean 규약(표준 방식), 메서드를 통한 property 접근
        public String getName() { // 라이브러리나 코드를 작성할 때도 메서드를 통해 프로퍼티 접근
            return name;
        }

        public void setName(String name) { // 메서드를 통해 접근
            this.name = name;
        }
    }

}
