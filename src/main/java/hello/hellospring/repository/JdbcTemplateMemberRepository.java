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

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    // jdbcTemplate를 injection을 받을 수 있는 건 아님
    // dataSource를 주입받을 것
    @Autowired // 생성자가 딱 하나면 생략 가능
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate((dataSource));
    }

    @Override
    public Member save(Member member) {
        // 자동으로 insert 쿼리를 짜줌. 테이블명이랑 PK가 있으니 파악 가능
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 두 번째 파라미터에 로우매퍼를 넣어야 함
        // 반환형 Optional로 맞추기
        // 이 자체는 리스트로 반환
//        return jdbcTemplate.query("select * from member where id=?", memberRowMapper(), id);

        List<Member> result = jdbcTemplate.query("select * from member where id=?", memberRowMapper(), id);
        return result.stream().findAny(); // Optional로 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name=?", memberRowMapper(), name);
        return result.stream().findAny(); // Optional로 반환
    }

    @Override
    public List<Member> findAll() {
        // 얘는 반환형이 리스트!
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    // Member 객체 생성 역할 콜백
    private RowMapper<Member> memberRowMapper() {
//        return new RowMapper<Member>() { // 람다로 변경 가능
//            @Override
//            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Member member = new Member();
//                member.setId(rs.getLong("id"));
//                member.setName(rs.getString("name"));
//                return member;
//            }
//        };
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
