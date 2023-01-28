package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // 기존에 쓰던 코드 (주석 후 실행 필수)
//    MemberService old_memberService = new MemberService();
//    MemoryMemberRepository old_memberRepository = new MemoryMemberRepository();

    // 위 코드에서 MemoryMemberRepository의 객체는 main코드와 test코드에서 동일하지 않다.
    // 같은 객체를 쓰려면 아래와 같이 변경 후, 의존성 주입을 해주면 된다.
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach // 각 테스트를 실행하기 전에
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository); // 이런 걸 DI 의존성 주입이라고 함
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given : 상황이 주어졌을 때
        Member member = new Member();
        member.setName("ㅇㅈㅇ");

        // when : 이걸 실행하면 (=이걸 검증하겠다)
        Long saveId = memberService.join(member);

        // then : 이 결과가 나와야 해
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복회원예외() {
        // given
        Member m1 = new Member();
        m1.setName("ㅇㅈㅇ");

        Member m2 = new Member();
        m2.setName("ㅇㅈㅇ");

        // when
        memberService.join(m1);

        try {
            memberService.join(m2);
            fail(); // 테스트 실패 처리

        // then
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

        // 개선 : try-catch -> assertThrows 사용
        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(m2));
        // 꼭 아래처럼 해야하는 건 아님. 위 코드로도 테스트 가능함.
        // 위 코드로 할 때는 assertThrows만 적으면 됨.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findOne() {
    }
}