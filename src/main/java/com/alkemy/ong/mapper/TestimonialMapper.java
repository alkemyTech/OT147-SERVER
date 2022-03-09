package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.entity.TestimonialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TestimonialMapper {

    TestimonialMapper testimonialMapper = Mappers.getMapper(TestimonialMapper.class);

    TestimonialEntity testimonialDtoToTestimonialEntity(TestimonialDto testimonialDto);

    TestimonialDto testimonialToTestimonialDto(TestimonialEntity testimonialEntity);
}
