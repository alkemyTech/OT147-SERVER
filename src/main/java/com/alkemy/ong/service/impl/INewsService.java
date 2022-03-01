package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class INewsService implements NewsService {

    private NewsMapper newsMapper;
    @Autowired
    private NewsRepository newsRepo;

    @Override
    public NewsDTO getDetailsById(String id) {
        System.out.println(id);
            NewsEntity newsEntity = newsRepo.getById(id);
            return  newsMapper.newsEntityToNewsDTO(newsEntity);
    }
}
