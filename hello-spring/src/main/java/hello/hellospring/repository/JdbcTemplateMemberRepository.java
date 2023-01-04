package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* JdbcTemplate 을 활용한 레포지토리 : 실무에서도 많이 씀 ! */
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired // 생성자가 1개라면 Autowired 생략 가능. 고로, 얘도 생략 가능!
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        // 왜 DataSource를 받는가? -> JdbcTemplate은 주입이 되는 애가 아니기 때문!
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert : 쿼리문 없이 쉽게 insert 쿼리의 처리가 가능
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
                    // member 테이블의 pk 컬럼으로 id 사용한다는 뜻
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
                    // 해당 컬럼명에 해당 값을 저장 -> 이 경우 name 컬럼에 받아온 이름 저장
        // Map 구조로 필요한 파라메터의 값 저장 후, 아래와 같이(line 38, 39) 수행하면 바로 pk 값을 얻을 수 있다.
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
                            // executeAndReturnKey() : 작업 수행과 동시에 자동 생성된 PK 반환
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {                                            // 매핑해주는 애. 아래에 정의
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();

    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ? ", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }




    /* 매핑을 위한 메서드 */
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            /* alt + Enter로 람다식 변환 적용함. 원래는 아래와 같이 생겼음 */
            // return new RowMapper<Member>() {
            //      @Override
            //      public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            //            Member member = new Member();
            //            member.setId(rs.getLong("id"));
            //            member.setName(rs.getString("name"));
            //            return member;
            //      }
            // }

            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
