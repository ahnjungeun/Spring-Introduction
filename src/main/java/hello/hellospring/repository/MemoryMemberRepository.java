package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class  MemoryMemberRepository implements MemberRepository{

    // 실무에선 동시성 문제가 있어서 ConcurrentHashMap<>을 써야 함
    private static Map<Long,Member> store = new HashMap<>();
    // long은 AtomicLong을 사용
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // return store.get(id)는 불가능. 반환형이 Member여서 안 됨.
        // 함수 반환형이 Optional이라 맞춰야 함.
        // null 반환 가능성이 있으면 ofNullable로 감싸기
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // loop 돌림
                // 파라미터로 받은 name과 store에 저장된 member의 name이 같은 것을
                .filter(member -> member.getName().equals(name)) // 람다 사용
                .findAny(); // 하나라도 찾으면 반환
    }

    @Override
    public List<Member> findAll() {
        // 실무에서 리스트 많이 씀 -> 루프 돌리기 편함
        // store에 있는 값들 전체 반환
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
