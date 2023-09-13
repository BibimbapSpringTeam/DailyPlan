package com.springboot.domain.follow.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.springboot.domain.follow.dto.FollowResponseDto;
import com.springboot.domain.follow.service.FollowService;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.global.result.ResultCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{memberId}/{followingMemberId}")
    public ResponseEntity<ResultResponse> follow(@PathVariable Long memberId, @PathVariable Long followingMemberId) throws FirebaseMessagingException {
        FollowResponseDto responseDto = followService.follow(memberId, followingMemberId);
        return ResponseEntity.ok(ResultResponse.of(FOLLOW_SUCCESS, responseDto));
    }

    @GetMapping("/followingList/{memberId}")
    public ResponseEntity<ResultResponse> getFollowingList(@PathVariable Long memberId) {
        List<FollowResponseDto> responseDtoList = followService.getFollowingList(memberId);
        return ResponseEntity.ok(ResultResponse.of(GET_FOLLOWING_LIST_SUCCESS, responseDtoList));
    }

    @GetMapping("/followers/{followingMemberId}")
    public ResponseEntity<ResultResponse> getFollowerList(@PathVariable Long followingMemberId) {
        List<FollowResponseDto> responseDtos = followService.getFollowers(followingMemberId);
        return ResponseEntity.ok(ResultResponse.of(GET_FOLLOWER_LIST_SUCCESS, responseDtos));
    }

    @DeleteMapping("/{memberId}/{followingMemberId}")
    public ResponseEntity<ResultResponse> unfollow(@PathVariable Long memberId, @PathVariable Long followingMemberId) {
        return ResponseEntity.ok(ResultResponse.of(UNFOLLOW_SUCCESS, followService.unfollow(memberId, followingMemberId)));
    }
}
