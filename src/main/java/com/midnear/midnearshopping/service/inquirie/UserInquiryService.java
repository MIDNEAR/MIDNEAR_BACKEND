package com.midnear.midnearshopping.service.inquirie;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiryRequestDto;
import com.midnear.midnearshopping.domain.vo.Inquiries.InquiriesVO;
import com.midnear.midnearshopping.domain.vo.claim.CanceledProductVO;
import com.midnear.midnearshopping.mapper.Inquiries.UserInquiryMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInquiryService {
    private UserInquiryMapper userInquiryMapper;
    private UsersMapper usersMapper;

    public void createInquiry(String id, InquiryRequestDto requestDto) {
        Integer userId =  usersMapper.getUserIdById(id);
        if(userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        try {
            userInquiryMapper.insertInquiry(InquiriesVO.toEntity(requestDto));
        } catch (Exception e) {
            throw new RuntimeException("문의글 작성중 오류 발생");
        }
    }

}
