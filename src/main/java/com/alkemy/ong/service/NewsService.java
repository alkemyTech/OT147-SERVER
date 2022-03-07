package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class NewsService {

    private final NewsMapper newsMapper;
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
    @Transactional
    public NewsDto save(NewsDto dto) {
        NewsEntity entity= newsMapper.newsDtoTonewsEntity(dto);
        return newsMapper.newsEntityToNewsDto(newsRepo.save(entity));
    }
    @Transactional
    public NewsDto update(String id, NewsDto dto) {
        var newsId=newsRepo.findById((id))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There is no News with the entered Id"));
        var news=newsRepo.save(newsMapper.newsDtoTonewsEntity(dto));
        return newsMapper.newsEntityToNewsDto(news);
    }
    @Transactional
    public void delete(String id) {
        if(newsRepo.existsById(id)){
            NewsEntity entity= newsRepo.getById(id);
            entity.setSoftDelete(true);
            newsRepo.save(entity);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no News with the entered Id");
        }
    }
}