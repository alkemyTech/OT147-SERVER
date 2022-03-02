package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepo;

    //Service to retrieve a specific news from database. Check if it exits.
    public NewsDto getDetailsById(String newsId) {
        Optional<NewsEntity> news = newsRepo.findById(newsId);
        if (news.isPresent()) {
            NewsDto newsDto = NewsMapper.newsMapper.newsEntityToNewsDto(news.get());
            return newsDto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There are no news with the provided id");
        }
    }
}