package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DisruptiveService {
    List<String> searchId(String id);

    void insertDisruptive(disruptiveDTO disruptiveDTO);
}
