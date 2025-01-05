package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveDTO;
import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveListDTO;
import com.midnear.midnearshopping.mapper.disruptive.DisruptiveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisruptiveServiceImpl implements DisruptiveService {

    private final DisruptiveMapper disruptiveMapper;
    private static final int pageSize = 2;
    @Override
    public List<String> searchId(String id) {
        return disruptiveMapper.searchId(id);
    }

    @Override
    public void insertDisruptive(disruptiveDTO disruptiveDTO) {
        disruptiveMapper.insertDisruptive(disruptiveDTO);
    }

    @Override
    public List<disruptiveListDTO> SelectDisruptlist(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return disruptiveMapper.SelectDisruptlist(offset, pageSize);
    }
    @Override
    public int count() {
        return disruptiveMapper.count();
    }
}
