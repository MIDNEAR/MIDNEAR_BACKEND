package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.member.MemberDto;
import com.midnear.midnearshopping.domain.vo.member.MemberVO;
import com.midnear.midnearshopping.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public String signUp(MemberDto memberDto) {
        if(memberMapper.isMemberExist(memberDto.getId())){
            return "이미 존재하는 아이디입니다.";
        }
        memberDto.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));
        memberMapper.createMember(MemberVO.toEntity(memberDto));
        return "회원가입 성공";
    }

    public Boolean isDuplicate(String id){
        return memberMapper.isMemberExist(id);
    }

}

