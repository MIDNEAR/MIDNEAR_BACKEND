package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveDTO;
import com.midnear.midnearshopping.mapper.disruptive.DisruptiveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisruptiveServiceImpl implements DisruptiveService {

    private final DisruptiveMapper disruptiveMapper;
    @Override
    public List<String> searchId(String id) {
        return disruptiveMapper.searchId(id);
    }

    @Override
    public void insertDisruptive(disruptiveDTO disruptiveDTO) {
        disruptiveMapper.insertDisruptive(disruptiveDTO);
    }
}
