package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDtoFull;
import com.alkemy.ong.entity.SlideEntity;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.SlideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    //Update Slide
    public SlideDtoFull update(String id, SlideDtoFull slideDtoFull) {
        if (slideRepository.findById(id).isPresent()) {
            SlideEntity slideEntity = slideRepository.findById(id).get();
            slideEntity.setImageUrl(slideDtoFull.getImageUrl());
            slideEntity.setText(slideDtoFull.getText());
            slideEntity.setOrder(slideDtoFull.getOrder());
            slideEntity.setOrganizationId(slideDtoFull.getOrganizationId());
            slideRepository.save(slideEntity);
            return SlideMapper.slideMapper.slideEntityToSlideDtoFull(slideEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "There is no Slide with the entered Id");
        }
    }
    public SlideDtoFull getSlide(String id){
        Optional<SlideEntity> slide= slideRepository.findById(id);
        if (slide.isPresent()){
            SlideDtoFull slideDtoFull= SlideMapper.slideMapper.slideEntityToSlideDtoFull(slide.get());
            slideDtoFull.setOrganizationId(slide.get().getOrganizationId());
        return slideDtoFull;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Slide with the entered id");
        }
    }

}
