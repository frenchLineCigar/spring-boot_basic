package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		System.out.println("============ 앱 구동 및 내장 톰캣 실행 =============");
		SpringApplication.run(HelloSpringApplication.class, args);
		System.out.println("============ 톰캣 실행 후 =============");
	}

}
