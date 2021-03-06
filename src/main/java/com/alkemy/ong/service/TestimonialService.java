package com.alkemy.ong.service;

import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.dto.TestimonialDtoFull;
import com.alkemy.ong.entity.TestimonialEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TestimonialService {

    private final TestimonialMapper testimonialMapper;


    @Autowired
    TestimonyRepository testimonialRepository;

    //Save testimonial
    @Transactional
    public TestimonialDto saveTestimonial(TestimonialDto testimonialDto) {
        TestimonialEntity testimonialEntity = testimonialMapper.testimonialDtoToTestimonialEntity(testimonialDto);
        TestimonialEntity savedTestimonialEntity = testimonialRepository.save(testimonialEntity);
        return testimonialMapper.testimonialToTestimonialDto(savedTestimonialEntity);
    }
    //Update testimonial
    @Transactional
    public TestimonialDto updateTestimonial(String id, TestimonialDto testimonialDto) {
        Optional<TestimonialEntity> testimonial = testimonialRepository.findById(id);
        if (testimonial.isPresent()) {
            TestimonialEntity testimonialEntity = testimonial.get();
            testimonialEntity.setName(testimonialDto.getName());
            testimonialEntity.setContent(testimonialDto.getContent());
            testimonialEntity.setImage(testimonialDto.getImage());
            testimonialRepository.save(testimonialEntity);
            return testimonialMapper.testimonialToTestimonialDto(testimonialEntity);
        } else {
            throw new ParamNotFound("The testimony is not found in the database.");
        }
    }
    //Delete Testimonial
    @Transactional
    public void deleteTestimonial(String id) {
        if(testimonialRepository.existsById(id)){
            TestimonialEntity testimonialEntity= testimonialRepository.getById(id);
            testimonialEntity.setSoftDelete(true);
            testimonialRepository.save(testimonialEntity);
        }else{
             throw new ParamNotFound("The testimony is not found in the database.");
        }
    }
    //Pagination of 10
    @Transactional
    public PagesDto<TestimonialDtoFull> searchPaginatedTestimonial(int page) {
        if (page < 0) {
            throw new ParamNotFound("The page number cannot be less than 0.");
        }
        Pageable pageRequest = PageRequest.of(page, 10);
        Page<TestimonialEntity> testimonial = testimonialRepository.findAll(pageRequest);
        return responsePage(testimonial);
    }

    private PagesDto<TestimonialDtoFull> responsePage(Page<TestimonialEntity> page) {
        if (page.isEmpty()) {
            throw new ParamNotFound("The page does not exist.");
        }
        Page<TestimonialDtoFull> response = new PageImpl<>(
                testimonialMapper.listTestimonialEntityToListTestimonialDtoFull(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize()),
                page.getTotalElements());
        return new PagesDto<>(response, "localhost:8080/testimonials?page=");
    }
}
