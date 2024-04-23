package gbsw.hs.kr.Health.controller;

import gbsw.hs.kr.Health.model.Signdto;
import gbsw.hs.kr.Health.model.Suceessdto;
import gbsw.hs.kr.Health.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoberController {
    private final MemberService memberService;

    public MemoberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/sign")
    public Suceessdto signProcess(@RequestBody Signdto signdto) {
        return memberService.signService(signdto);
    }
}
