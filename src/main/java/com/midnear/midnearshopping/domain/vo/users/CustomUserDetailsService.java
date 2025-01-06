package com.midnear.midnearshopping.domain.vo.users;

import com.midnear.midnearshopping.domain.dto.users.UsersDto;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
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
    private final UsersMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String id) {
        UsersVO member = memberMapper.getMemberById(id);
        if(member == null){
            throw new UsernameNotFoundException("해당하는 유저가 없습니다.");
        }
        UsersDto dto = UsersDto.toDto(member);
        return new CustomUserDetails(dto);
    }
}