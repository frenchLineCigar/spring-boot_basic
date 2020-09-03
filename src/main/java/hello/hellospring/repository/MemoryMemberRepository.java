package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by frenchline707@gmail.com on 2020-08-19
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/* 메모리 구현체 */
public class MemoryMemberRepository implements MemberRepository { //메모리에 저장

    private static final Map<Long, Member> store = new HashMap<>(); //실무에서는 `동시성 문제`가 있을 수 있어서 `공유되는 변수` 일 때는 ConcurrentHashMap을 써야되는데, 예제이므로 단순하게 HashMap을 사용
    private static long sequence = 0L; // map의 키값을 생성해주는 시퀀스, 실무에서는 AtomicLong 타입을 사용해 동시성 문제를 고려

    @Override
    public Member save(Member member) { /* 회원 저장 */
        member.setId(++sequence); //멤버를 저장할 때 시퀀스 값을 하나 올려준 뒤, 그 값을 member객체의 id값으로 할당
        store.put(member.getId(), member); //시퀀스를 키로 저장
        return member; //저장된 결과를 반환
    }

    @Override
    public Optional<Member> findById(Long id) { /* ID로 회원 조회 */
        return Optional.ofNullable(store.get(id)); // 조회 결과가 null이어도 Optinal로 감싸서 반환을 해주면,클라이언트에서 처리할 수가 있다 (Java8 Optional)
    }

    @Override
    public Optional<Member> findByName(String name) { /* 이름으로 회원 조회 */
        return store.values().stream() // 루프를 돌린다 (Java8 Stream)
                .filter(member -> member.getName().equals(name)) // 필터: member.getName()이 파라미터로 넘어온 name이랑 같은 경우에만 필터링 (Java8 Lambda Expressions)
                .findAny(); //그 중에서 하나 찾아지면 그걸 바로 반환
        /**
         * findAny() : 하나라도 찾으면 그 결과가 Optonal로 반환이 된다
         * Map 객체인 store에서 루프를 다 돌면서 조건 대로 하나 찾아지면 그것을 반환하고,
         * 끝까지 돌렸는데 없으면 Optional에 null이 포함되서 반환한다
         */

    }

    @Override
    public List<Member> findAll() { /* 전체 회원 조회 */
        return new ArrayList<>(store.values());
//        List<Member> result = new ArrayList<>();
//        result = (List<Member>) store.values();
//        return result;
    }

    public void clearStore() {
        store.clear();
    }
}
