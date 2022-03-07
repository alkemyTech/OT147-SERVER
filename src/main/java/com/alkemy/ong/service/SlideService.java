package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.entity.SlideEntity;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.SlideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class SlideService {
    private final SlideMapper slideMapper;

    @Autowired
    SlideRepository slideRepository;
    //Get all Slides from Database only image and order by Admin
    public List<SlideBasicDto> getAllSlides() {
        List<SlideEntity> slideEntityList = slideRepository.findAll();
        return slideMapper.listSlideEntityToListSlideBasicDto(slideEntityList);
    }
    //Slides Soft deletion method by id for Admin
    public void deleteSlideById(String id) throws Exception {
        slideRepository.deleteById(id);
    }

    //Get slides for id organization by camp order
    public List<SlideDto> getSlidesForOrganizationByOrder(String id) throws Exception {
        List<SlideEntity> slideEntityList;
        try {
            slideEntityList = slideRepository.findSlideByOrganizationId(id);
        } catch (Exception e) {
            throw new Exception("Organization no exist");
        }
        return slideMapper.listSlideEntityToListSlideDto(slideEntityList);
    }

}
