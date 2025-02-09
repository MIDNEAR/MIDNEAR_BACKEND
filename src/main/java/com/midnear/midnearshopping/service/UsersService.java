package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.users.LoginDto;
import com.midnear.midnearshopping.domain.dto.users.UserInfoChangeDto;
import com.midnear.midnearshopping.domain.dto.users.UsersDto;
import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import com.midnear.midnearshopping.jwt.JwtUtil;
import com.midnear.midnearshopping.mapper.cart.CartMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final UsersMapper usersMapper;
    private final CartMapper cartMapper;

    @Transactional
    public void signUp(UsersDto memberDto) {
        if(memberMapper.isMemberExist(memberDto.getId())){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        if(memberMapper.isMemberExistByPhone(memberDto.getId())){
            throw new IllegalArgumentException("이미 가입된 전화번호 입니다.");
        }
        memberDto.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));
        UsersVO vo = UsersVO.toEntity(memberDto);
        memberMapper.createMember(vo);
        cartMapper.createCart(vo.getUserId());

    }

    @Transactional
    public String login(LoginDto loginDto) {
        UsersVO member = memberMapper.getMemberById(loginDto.getId());
        if(member == null){
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }
        if(!bCryptPasswordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        UsersDto memberDto = UsersDto.toDto(member);
        return jwtUtil.createAccessToken(memberDto);

    }

    public Boolean isDuplicate(String id){
        return memberMapper.isMemberExist(id);
    }

    public String findIdByPhone(String phone) {
        UsersVO user = usersMapper.getMemberByPhone(phone);
        if (user == null) {
            throw new UsernameNotFoundException("해당 번호로 가입된 아이디가 존재하지 않습니다. 문제가 계속 될 경우 고객센터로 문의하시기 바랍니다.");
        }
        if (user.getSocialType() == null) {
            return user.getId();
        }
        throw new IllegalArgumentException("소셜 계정으로 가입된 전화번호 입니다. 소셜 로그인을 시도해주세요.");
    }

    public String findIdByEmail(String email) {
        UsersVO user = usersMapper.getMemberByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("해당 이메일로 가입된 아이디가 존재하지 않습니다. 문제가 계속 될 경우 고객센터로 문의하시기 바랍니다.");
        }
        if (user.getSocialType()==null) {
            return user.getId();
        }
        throw new IllegalArgumentException("소셜 계정으로 가입된 이메일 입니다. 소셜 로그인을 시도해주세요.");
    }

    @Transactional
    public String changePassword(String id, String password) {
        UsersVO user = usersMapper.getMemberById(id);
        if (user == null) {
            throw new UsernameNotFoundException("해당 번호로 가입된 아이디가 존재하지 않습니다. 문제가 계속 될 경우 고객센터로 문의하시기 바랍니다.");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        user.setPassword(encodedPassword);
        int updateCount = usersMapper.updatePassword(user);

        if (updateCount == 0) {
            throw new RuntimeException("비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
        }
        return user.getId();
    }

    public Boolean checkPassword(String id, String password) {
        String originPwd = memberMapper.getPasswordById(id);
        return bCryptPasswordEncoder.matches(password, originPwd);
    }

    public UserInfoChangeDto getUserInfo(String id){
        UsersVO user = usersMapper.getMemberById(id);
        if (user == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        return UserInfoChangeDto.toDto(user);

    }

    @Transactional
    public void changeUserInfo(UserInfoChangeDto userInfoChangeDto) {
        UsersVO user = usersMapper.getMemberById(userInfoChangeDto.getId());
        if (user == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        if (user.getSocialType()!=null) {
            throw new UsernameNotFoundException("소셜 계정으로 가입된 회원은 이메일 변경이 불가능 합니다.");
        }
        usersMapper.updateUserInfo(userInfoChangeDto);



    }



}

