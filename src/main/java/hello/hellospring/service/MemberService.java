package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public class MemberService {
    // 기존에 쓰던 코드
    private final MemberRepository old_memberRepository = new MemoryMemberRepository();

    // 위 코드를 test코드와 같은 객체를 사용하기 위해 아래와 같이 변경
    private final MemberRepository memberRepository;

//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 주석쓰기 /** 치고 엔터

    /**
     * 회원가입
     *
     * @param member
     * @return
     */
    public Long join(Member member) {
//        // 같은 이름은 회원가입이 불가능하게, 중복가입방지
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        // result에 값이 있으면 람다 실행
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });
//
//        // 개선 1 : Optional로 나오면 코드가 안 예쁨
//        memberRepository.findByName(member.getName()) // 이 값이 Optional이니까 바로 반환해서 사용
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });

        // 개선 2 : 이런 비즈니스 로직은 메소드로 분리하는 것이 낫다.
        validateDuplicateMember(member); // 최종

        // 기본 회원가입은 save만 하면 끝
        memberRepository.save(member);
        return member.getId();
    }

    // 개선 2 - 메소드 추출 부분
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한 명 조회
     * @param memberId
     * @return
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
