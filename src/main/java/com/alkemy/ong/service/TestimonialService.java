package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.entity.TestimonialEntity;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class TestimonialService {

    private final TestimonialMapper testimonialMapper;

    @Autowired
    TestimonyRepository testimonialRepository;

    //Save testimonial
    public TestimonialDto saveTestimonial(TestimonialDto testimonialDto) {
        TestimonialDto validTestimonialDto = this.validate(testimonialDto);
        TestimonialEntity testimonialEntity = testimonialMapper.testimonialDtoToTestimonialEntity(validTestimonialDto);
        TestimonialEntity savedTestimonialEntity = testimonialRepository.save(testimonialEntity);
        return testimonialMapper.testimonialToTestimonialDto(savedTestimonialEntity);
    }

    //Validate if testimonialDto is ok;
    public TestimonialDto validate(TestimonialDto testimonialDto) throws ResponseStatusException {
        if (testimonialDto.getName().trim().isEmpty() && testimonialDto.getContent().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"The name and content fields can not be empty");
        } else if (testimonialDto.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"The name can not be empty");
        } else if (testimonialDto.getContent().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"The content can not be empty");
        }
        return testimonialDto;
    }
}
