package com.springboot.domain.member.controller;

import com.springboot.domain.member.dto.MemberRequestDto;
import com.springboot.domain.member.dto.MemberResponseDto;
import com.springboot.domain.member.service.MemberService;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.global.result.ResultCode.MEMBER_SAVE_OR_UPDATE_SUCCESS;

@RequiredArgsConstructor    //final이 붙은 필드에 대해 생성자 자동생성
//@Controller
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/")
    public ResponseEntity<ResultResponse> post(@RequestBody MemberRequestDto requestDto) {
         MemberResponseDto responseDto = memberService.post(requestDto);
         return ResponseEntity.ok(ResultResponse.of(MEMBER_SAVE_OR_UPDATE_SUCCESS, responseDto));
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




