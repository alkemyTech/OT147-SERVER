package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
        NewsEntity entity = newsMapper.newsDtoTonewsEntity(dto);
        return newsMapper.newsEntityToNewsDto(newsRepo.save(entity));
    }

    @Transactional
    public NewsDto update(String id, NewsDto dto) {
        var newsId = newsRepo.findById((id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There is no News with the entered Id"));
        var news = newsRepo.save(newsMapper.newsDtoTonewsEntity(dto));
        return newsMapper.newsEntityToNewsDto(news);
    }

    @Transactional
    public void delete(String id) {
        if (newsRepo.existsById(id)) {
            NewsEntity entity = newsRepo.getById(id);
            entity.setSoftDelete(true);
            newsRepo.save(entity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no News with the entered Id");
        }
    }

    public PagesDto<NewsDto> getAllPagesNews(int page) {
        if (page < 0) {
            throw new ParamNotFound("The page number cannot be less than 0.");
        }
        Pageable pageRequest = PageRequest.of(page, 10);
        Page<NewsEntity> news = newsRepo.findAll(pageRequest);
        return responsePage(news);
    }

    private PagesDto<NewsDto> responsePage(Page<NewsEntity> page) {
        if (page.isEmpty()) {
            throw new ParamNotFound("The page does not exist.");
        }
        Page<NewsDto> response = new PageImpl<>(newsMapper.newsEntityToNewsDto(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
        return new PagesDto<>(response, "localhost:8080/categories?page=");
    }
}