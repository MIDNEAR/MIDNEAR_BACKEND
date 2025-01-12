package com.midnear.midnearshopping.controller;


import com.midnear.midnearshopping.domain.dto.users.LoginDto;
import com.midnear.midnearshopping.domain.dto.users.UsersDto;
import com.midnear.midnearshopping.exception.ApiResponse;


import com.midnear.midnearshopping.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {
    private final UsersService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UsersDto memberDto) {
        try {
            memberService.signUp(memberDto);
            return ResponseEntity.ok(new ApiResponse(true, "회원가입 성공", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            String token = memberService.login(loginDto);
            return ResponseEntity.ok(new ApiResponse(true, "로그인 성공", token));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @GetMapping("/isDuplicate")
    public ResponseEntity<String> isDuplicate(@RequestParam String id) {
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

    @GetMapping("/findId")
    public ResponseEntity<?> findId(@RequestParam String phone) {
        try {
            String id = memberService.findIdByPhone(phone);
            return ResponseEntity.ok(new ApiResponse(true, "아이디 찾기 성공", id));
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
}
