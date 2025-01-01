package com.midnear.midnearshopping.domain.vo.member;

import com.midnear.midnearshopping.domain.dto.member.MemberDto;
import com.midnear.midnearshopping.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String id) {
        MemberVO member = memberMapper.getMemberById(id);
        if(member == null){
            throw new UsernameNotFoundException("해당하는 유저가 없습니다.");
        }
        MemberDto dto = MemberDto.toDto(member);
        return new CustomUserDetails(dto);
    }
}