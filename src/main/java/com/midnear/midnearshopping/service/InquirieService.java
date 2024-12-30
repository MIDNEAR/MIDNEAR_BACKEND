package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import org.springframework.stereotype.Service;

@Service
public interface InquirieService {
    InquiriesDTO selectInquirie(Long inquiry_id);
}
