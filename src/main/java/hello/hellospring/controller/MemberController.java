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

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm memberForm) {
        Member m = new Member();
        m.setName(memberForm.getName());

        memberService.join(m);

        return "redirect:/"; // 회원가입 후 홈화면으로 보냄
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> ms = memberService.findMembers();
        model.addAttribute("members", ms);
        return "members/memberList";
    }
}
