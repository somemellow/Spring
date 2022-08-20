package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
//Command + Shift + T 는 테스트를 만드는 단축키

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public long join(Member member) {
        validateDuplicateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //같은 이름으로 중복 회원가입 X
        //로직이 나오는 부분은 메서드로 뽑는게 좋다
        //control+T는 리팩토링 항목들이 나오는 단축키
        //Commad + Option + M 은 리팩토링으로 메소드를 만들어 주는 단축키
        memberRepository.findByName(member.getName())
            .ifPresent(m -> { //ifPresent는 값이 있으면 이라는 함수 ifPresent의 매개변수인 member가 추론이 되어지기 때문에 람다 표현식 사용
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }
    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findone(Long memberId){
        return memberRepository.findById(memberId);
    }
}
