package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    public OrderServiceImpl(MemberRepository memberRepository,@MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        //이 경우는 설계가 잘된 것 orderservice 입장에선 discountpolicy가
        //어떤걸 하는지 모르고 discount에서 할인된 가격의 결과만 알려줘
        //단일 책임원칙을 잘 지킨 것이다.
        //할인에 대한 변경이 필요할경우 할인쪽만 변경하면 된다.
        //만약 할인정책 클래스가 없을 경 오더 서비스에서 할인과 관련된 변경이 있을 경우 오더 서비스를 고쳐야 한다.
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    //Test 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
