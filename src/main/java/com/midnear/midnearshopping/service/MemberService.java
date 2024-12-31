package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.member.LoginDto;
import com.midnear.midnearshopping.domain.dto.member.MemberDto;
import com.midnear.midnearshopping.domain.vo.member.MemberVO;
import com.midnear.midnearshopping.jwt.JwtUtil;
import com.midnear.midnearshopping.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(MemberDto memberDto) {
        if(memberMapper.isMemberExist(memberDto.getId())){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        memberDto.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));
        memberMapper.createMember(MemberVO.toEntity(memberDto));

    }

    @Transactional
    public String login(LoginDto loginDto) {
        MemberVO member = memberMapper.getMemberById(loginDto.getId());
        if(member == null){
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }
        if(!bCryptPasswordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        MemberDto memberDto = MemberDto.toDto(member);
        return jwtUtil.createAccessToken(memberDto);

    }

    public Boolean isDuplicate(String id){
        return memberMapper.isMemberExist(id);
    }

}

