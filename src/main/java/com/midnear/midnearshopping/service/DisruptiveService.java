package com.midnear.midnearshopping.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DisruptiveService {
    List<String> searchId(String id);
}
