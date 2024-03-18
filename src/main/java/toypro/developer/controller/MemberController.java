package toypro.developer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toypro.developer.domain.Member;
import toypro.developer.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public List<Member> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return members;
    }
}
