package com.springboot.domain.member.controller;

import com.springboot.domain.member.dto.MemberLogInRequestDto;
import com.springboot.domain.member.dto.MemberResponseDto;
import com.springboot.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor    //final이 붙은 필드에 대해 생성자 자동생성
//@Controller
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/")
    public MemberResponseDto post(@RequestBody MemberLogInRequestDto requestDto) {
        return memberService.logIn(requestDto);
    }

    @GetMapping("/{member_id}")
    public MemberResponseDto get(@PathVariable Long member_id) {
        return memberService.get(member_id);
    }

    @GetMapping("/email/{email}")
    public MemberResponseDto getEmail(@PathVariable String email) {
        return memberService.getEmail(email);
    }

}




