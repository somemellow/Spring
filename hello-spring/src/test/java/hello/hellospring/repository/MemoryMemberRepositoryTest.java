package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 클래스 단계 테스트의 메서드 실행 순서는 랜덤이다.
 * 그렇기 때문에 findByName 메소드와 findAll 메소드의
 * 멤버 이름이 겹치기 때문에 클래스 단계 테스트에서 오류가 발생하게 된다.
 * 이 때 메모리 값을 클리어 시켜 주어야 하기 때문에 항상 메소드의 테스트가
 * 끝나면 메모리 값을 클리어 시켜주는 메소드를를작성해야 한다.
 *
 * 테스트는 서로 순서와 관계없이 서로 의존성이 없어야 한다
 *
 * 테스트 케이스를 먼저 만들고 테스트 케이스에 맞도록 클래스 구현을 하는 개발방식을
 * 테스트 주도 개발(TDD) 이라고 한다
 * */
public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach //AfterEach 어노테이션은 메소드가 끝나고 나서 실행 시켜준다는 의미의 어노테이션 이다.
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertEquals(member, result);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        //Shift F6 으로 변수명을 한번에 변경
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result =repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
