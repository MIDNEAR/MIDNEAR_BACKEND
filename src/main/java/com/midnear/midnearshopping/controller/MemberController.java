package com.midnear.midnearshopping.controller;


import com.midnear.midnearshopping.domain.dto.member.MemberDto;
import com.midnear.midnearshopping.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody MemberDto memberDto) {
        try {
            String result = memberService.signUp(memberDto);
            if ("이미 존재하는 아이디입니다.".equals(result)) {
                return ResponseEntity.status(409).body(result); //중복시
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("회원가입 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/isDuplicate")
    public ResponseEntity<String> isDuplicate(String id) {
        try {
            Boolean result = memberService.isDuplicate(id);
            if (result) {
                return ResponseEntity.status(409).body("이미 존재하는 아이디 입니다."); //중복시
            }
            return ResponseEntity.ok("사용 가능한 아이디 입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("중복확인 중 오류가 발생했습니다.");
        }

    }
}
