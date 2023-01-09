package hello.hellospring.Repository;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("정은");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();

        // 아래처럼 프린트로 확인해도 되지만 매 번 확인할 수 없음
        System.out.println(result == member);

        // 라이브러리 사용하기
        // junit
        // 파라미터 (expect, actual) 기대와 실제
        Assertions.assertEquals(result, member);

        // assertj
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member m1 = new Member();
        m1.setName("m1");
        repository.save(m1);

        Member m2 = new Member();
        m2.setName("m2");
        repository.save(m2);

        Member res1 = repository.findByName("m1").get();

        assertThat(res1).isEqualTo(m1);
    }

    @Test
    public void findByAll() {
        Member m1 = new Member();
        m1.setName("m1");
        repository.save(m1);

        Member m2 = new Member();
        m2.setName("m2");
        repository.save(m2);

        List<Member> resAll = repository.findAll();

        assertThat(resAll.size()).isEqualTo(2);
    }
}
