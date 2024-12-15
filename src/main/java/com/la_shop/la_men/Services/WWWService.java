package com.la_shop.la_men.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.la_shop.la_men.Entities.what_to_wear_with;
import com.la_shop.la_men.repo.WWWRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WWWService {

    private final WWWRepo wwwRepo;

    public void save(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<what_to_wear_with> data = objectMapper.readValue(file.getBytes(), new TypeReference<>() {
        });
        wwwRepo.saveAll(data);
    }

}
