package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDTO;

public interface NewsService {
    NewsDTO getDetailsById(String id);
}
