package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.entity.TestimonialEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        TestimonialDto validTestimonialDto = this.validate(testimonialDto);
        TestimonialEntity testimonialEntity = testimonialMapper.testimonialDtoToTestimonialEntity(validTestimonialDto);
        TestimonialEntity savedTestimonialEntity = testimonialRepository.save(testimonialEntity);
        return testimonialMapper.testimonialToTestimonialDto(savedTestimonialEntity);
    }
    //Update testimonial
    @Transactional
    public TestimonialDto updateTestimonial(String id, TestimonialDto testimonialDto) {
        Optional<TestimonialEntity> testimonial = testimonialRepository.findById(id);
        TestimonialDto validTestimonialDto=this.validate(testimonialDto);
        if (testimonial.isPresent()) {
            TestimonialEntity testimonialEntity = testimonial.get();
            testimonialEntity.setName(validTestimonialDto.getName());
            testimonialEntity.setContent(validTestimonialDto.getContent());
            testimonialEntity.setImage(validTestimonialDto.getImage());
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
    //Validate if testimonialDto is ok;
    public TestimonialDto validate(TestimonialDto testimonialDto) throws ResponseStatusException {
        if (testimonialDto.getName().trim().isEmpty() && testimonialDto.getContent().trim().isEmpty()) {
            throw new ParamNotFound("The name and content fields can not be empty");
        } else if (testimonialDto.getName().trim().isEmpty()) {
            throw new ParamNotFound("The name can not be empty");
        } else if (testimonialDto.getContent().trim().isEmpty()) {
            throw new ParamNotFound("The name content not be empty");
        }
        return testimonialDto;
    }
}
