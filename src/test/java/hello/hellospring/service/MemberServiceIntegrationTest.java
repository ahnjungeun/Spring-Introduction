package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
//    @Autowired MemoryMemberRepository memberRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("오잉");

        Long saveId = memberService.join(member);

        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복회원예외() {
        Member m1 = new Member();
        m1.setName("ㅇㅈㅇ");

        Member m2 = new Member();
        m2.setName("ㅇㅈㅇ");

        memberService.join(m1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(m2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}