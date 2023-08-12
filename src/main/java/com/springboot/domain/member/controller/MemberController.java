package com.springboot.domain.member.controller;

import com.springboot.domain.member.dto.MemberRequestDto;
import com.springboot.domain.member.dto.MemberResponseDto;
import com.springboot.domain.member.service.MemberService;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.global.result.ResultCode.*;

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
    public ResponseEntity<ResultResponse> get(@PathVariable Long member_id) {
        MemberResponseDto responseDto = memberService.get(member_id);
        return ResponseEntity.ok(ResultResponse.of(GET_USERPROFILE_SUCCESS, responseDto));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResultResponse> getEmail(@PathVariable String email) {
        MemberResponseDto responseDto = memberService.getEmail(email);
        return ResponseEntity.ok(ResultResponse.of(GET_USERPROFILE_SUCCESS, responseDto));
    }

}




