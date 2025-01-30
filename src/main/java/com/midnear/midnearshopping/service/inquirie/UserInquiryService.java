package com.midnear.midnearshopping.service.inquirie;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesListDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiryRequestDto;
import com.midnear.midnearshopping.domain.dto.Inquiries.UserInquiryListDto;
import com.midnear.midnearshopping.domain.vo.Inquiries.InquiriesVO;
import com.midnear.midnearshopping.domain.vo.claim.CanceledProductVO;
import com.midnear.midnearshopping.mapper.Inquiries.UserInquiryMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInquiryService {
    private UserInquiryMapper userInquiryMapper;
    private UsersMapper usersMapper;
    private static final int pageSize = 2;

    @Transactional
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
    @Transactional
    public List<UserInquiryListDto> selectInquirylist(int pageNumber) {
        try {
            int offset = (pageNumber - 1) * pageSize;
            return userInquiryMapper.SelectInquirylist(offset, pageSize);
        } catch (Exception e) {
            throw new RuntimeException("문의글 리스트 조회중 오류 발생");
        }
    }

    @Transactional
    public InquiriesDTO selectInquiry(Long inquiryId) {
        try {
            userInquiryMapper.updateViewCount(inquiryId);
            return userInquiryMapper.selectInquiries(inquiryId);
        } catch (Exception e) {
            throw new RuntimeException("문의글 조회중 오류 발생");
        }
    }



}
