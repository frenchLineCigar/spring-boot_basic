package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by frenchline707@gmail.com on 2020-08-21
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 스프링 JdbcTemplate
 * - 순수 Jdbc와 동일한 환경설정을 하면 된다 (build.gradle, application.properties)
 * - 스프링 JdbcTemplate과 MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해준다. 하지만 SQL은 직접 작성해야 한다
 */
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    //@Autowired /* 생성자가 딱 하나일 경우 @Autowired 생략가능. 생략해도 스프링이 dataSource를 자동으로 인젝션 해줌 */
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); //테이블 이름과 PK를 셋팅하면 Insert쿼리를 짤 필요가 없음

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    /* id로 회원 조회 */
    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny(); //List를 스트림으로 바꿔서 findAny후 Optional로 반환
    }

    /*템플릿 메서드 패턴 (Template Method Pattern)과 콜백
    * JdbcTemplate Libraty
    * 순수 JDBC API를 사용해 만들었던 코드와 비교하면 코드량이 아주 기가 막히게 줄어들었다
    * 왜 템플릿이냐면? 여러가지 의미가 있겠지만, 디자인 패턴(Design Pattern) 중에 `템플릿 메서드 패턴` 이란 것이 있다.
    * 그런 방식으로 해결하도록 기능이 삽입돼있고 순수 JDBC와 비교해 코드량을 많이 줄였다.
    *
    * 자주 쓰는 메서드
    * <T> List<T>	query(String sql, RowMapper<T> rowMapper)
    * -> RowMapper를 통해 각 행을 결과 개체에 매핑하여 정적 SQL이 지정된 쿼리를 실행합니다.
    * <T> List<T>	query(String sql, RowMapper<T> rowMapper, Object... args)
    * -> 주어진 SQL을 쿼리하여 SQL에서 준비된 문과 쿼리에 바인딩 할 인수 목록을 만들고 RowMapper를 통해 각 행을 결과 개체에 매핑합니다.
    *
    * */

    /* 이름으로 회원조회 */
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    /* 전체회원 조회 */
    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    /* 결과를 매핑할 RowMapper */
    /* RowMapper<T> 인터페이스를 해당 도메인 객체에 맞게 구현한다 */
    private RowMapper<Member> memberRowMapper() {

//        return new RowMapper<Member>() {
//            @Override
//            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Member member = new Member();
//                member.setId(rs.getLong("id")); //또는 member.setId(rs.getLong(1));
//                member.setName(rs.getString("name")); // 또는 member.setName(rs.getString(2));
//                return member;
//            }
//        };
        /* 위 코드를 Java8 Ramda로 변경 : [IntelliJ Tip] RowMapper<Member> 커서에서, Alt + Enter -> Replace with ramda */
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id")); //또는 member.setId(rs.getLong(1));
            member.setName(rs.getString("name")); // 또는 member.setName(rs.getString(2));
            return member;
        };
    }
}
