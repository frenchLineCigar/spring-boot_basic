package hello.hellospring.controller;

/**
 * Created by frenchline707@gmail.com on 2020-08-20
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* 웹 등록 화면에서 데이터를 전달 받을 폼 객체 */
public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
