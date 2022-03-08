package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDtoFull;
import com.alkemy.ong.entity.OrganizationEntity;
import com.alkemy.ong.entity.SlideEntity;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.util.Base64DecodedMultiPartFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SlideService {

    private final SlideMapper slideMapper;

    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    AmazonClient amazonClient;
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
    public List<SlideDtoFull> getSlidesForOrganizationByOrder(String organizationId) throws Exception {
        List<SlideEntity> slideEntityList;
        try {
            slideEntityList = slideRepository.findSlideByOrganizationId(organizationId);
        } catch (Exception e) {
            throw new Exception("The Organization id provided does not exits");
        }
        return slideMapper.listSlideEntityToListSlideDtoFull(slideEntityList);
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

    //Create Slide implement Base64 Decoded
    @Transactional
    public SlideDtoFull createSlide(SlideDtoFull slideDtoFull) throws Exception {

        SlideEntity slideEntity = slideMapper.slideDtoFullToSlideEntity(slideDtoFull);
        Optional<OrganizationEntity> organizationEntityOpc = organizationRepository.findById(slideEntity.getOrganizationId().getId());
        if (!organizationEntityOpc.isPresent()) {
            new Exception(String.format("Organization with ID not found", slideEntity.getOrganizationId().getId()));
        }
        slideEntity.setOrganizationId(organizationEntityOpc.get());
        if (slideEntity.getOrder() == null) {
            Optional<SlideEntity> slideEntityOptional = slideRepository.findById(slideEntity.getOrganizationId().getId());
            if (slideEntityOptional != null) {
                slideEntity.setOrder(slideEntityOptional.get().getOrder() + 1);
            } else {
                SlideEntity slideEntityMaxOrder = slideEntityOptional.get();
                slideEntity.setOrder(slideEntityMaxOrder.getOrder() + 1);
            }
        }

        MultipartFile decodedImage = decodeBase64Image2MultipartFile(slideDtoFull.getImageUrl());
        slideEntity.setImageUrl(amazonClient.uploadFileBase64(decodedImage).getFileUrl());

        SlideEntity entityUpdated = slideRepository.save(slideEntity);
        return slideMapper.slideEntityToSlideDtoFull(entityUpdated);

    }

    private MultipartFile decodeBase64Image2MultipartFile(String image64) {

        String[] baseString = image64.split(",");

        byte[] byteArray = new byte[0];
        byteArray = Base64.getDecoder().decode(baseString[1]);

        for (int i = 0; i < byteArray.length; ++i) {
            if (byteArray[i] < 0) {
                byteArray[i] += 256;
            }
        }
        return new Base64DecodedMultiPartFile(byteArray, baseString[0]);
    }

}
