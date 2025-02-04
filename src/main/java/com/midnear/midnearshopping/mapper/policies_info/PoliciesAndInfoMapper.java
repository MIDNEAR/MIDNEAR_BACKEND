package com.midnear.midnearshopping.mapper.policies_info;

import com.midnear.midnearshopping.domain.vo.policies_info.PoliciesAndInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PoliciesAndInfoMapper {
    String getPrivacyPolicy();
    String getTermsOfService();
    String getBusinessInfo();
    String getDataUsage();
    void insertData(PoliciesAndInfoVo policiesAndInfoVo);
    void deleteData(String type);
}
