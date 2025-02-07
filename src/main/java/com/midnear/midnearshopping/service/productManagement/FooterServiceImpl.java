package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.mapper.storeManagement.FooterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FooterServiceImpl implements FooterService {
    private final FooterMapper footerMapper;
    @Override
    public void updateFooter(String footerContents) {
        footerMapper.footerUpdate(footerContents);
    }
}
