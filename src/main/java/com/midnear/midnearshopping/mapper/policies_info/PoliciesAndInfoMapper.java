package com.midnear.midnearshopping.mapper.policies_info;

import com.midnear.midnearshopping.domain.vo.policies_info.PoliciesAndInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PoliciesAndInfoMapper {
    PoliciesAndInfoVo getPrivacyPolicy();
    PoliciesAndInfoVo getTermsOfService();
    PoliciesAndInfoVo getBusinessInfo();
    PoliciesAndInfoVo getDataUsage();
    void insertData(PoliciesAndInfoVo policiesAndInfoVo);
    void deleteData(String type);
}
