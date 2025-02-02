package com.midnear.midnearshopping.mapper.users;

import com.midnear.midnearshopping.domain.dto.users.UserInfoChangeDto;
import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.List;

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
    String getPasswordById(String id);
    void updateUserInfo(UserInfoChangeDto userInfoChangeDto);
    Integer getUserIdById(String id);
    List<String> getAllId();
    void addPointsToUser(@Param("id") String id, @Param("amount")Long amount);
    List<String> findUserByIdPaging(@Param("id")String id, @Param("offset")int offset);
    Long getPageSize(String id);
    List<Long> getAllUserId();
    void discountPointsToUserByUserId(Integer userId, Long amount);
    Decimal getPointAmount(Integer userId);
    String getIdByUserId(Long userId);
}
