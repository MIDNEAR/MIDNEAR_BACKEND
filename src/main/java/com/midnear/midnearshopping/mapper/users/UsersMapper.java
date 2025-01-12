package com.midnear.midnearshopping.mapper.users;

import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    void createMember(UsersVO member);
    Boolean isMemberExist(String Id);
    UsersVO getMemberById(String Id);
    UsersVO getMemberByUserId(Integer userId);
    UsersVO getMemberByPhone(String phone);
    UsersVO getMemberByEmail(String email);
    Boolean isMemberExistByPhone(String phone);
    int updatePassword(UsersVO user);
}
