package com.midnear.midnearshopping.mapper.member;

import com.midnear.midnearshopping.domain.vo.member.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void createMember(MemberVO member);
    Boolean isMemberExist(String Id);
    MemberVO getMemberById(String Id);
    MemberVO getMemberByUserId(Integer userId);
}
