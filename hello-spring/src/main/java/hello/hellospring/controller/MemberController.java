package hello.hellospring.controller;


import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    //새로운 서비스를 쓰는 것 보다 스프링 컨테이너에 등록을하고 사용하는게 좋다
    //private final MemberService memberService = new MemberService();
    /*
    현재 MemberService는 순수 자바 Class 이기 때문에 실행 시 Could not be found 에러가 발생한다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
     */
    /*
    //DI의 3가지 방법

    //필드 주입
    @Autowired private MemberService memberService;

    //setter 주입
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    */
    //생성자 주입 제일 권장하는 DI 방법 조립 시점에 생성자 주입으로 조립 해놓고 뒤로 변경 할 수 없기 때문에.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
