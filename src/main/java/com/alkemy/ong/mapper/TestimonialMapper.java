package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.dto.TestimonialDtoFull;
import com.alkemy.ong.entity.TestimonialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestimonialMapper {

    TestimonialMapper testimonialMapper = Mappers.getMapper(TestimonialMapper.class);

    TestimonialEntity testimonialDtoToTestimonialEntity(TestimonialDto testimonialDto);

    TestimonialDto testimonialToTestimonialDto(TestimonialEntity testimonialEntity);

    List<TestimonialDtoFull> listTestimonialEntityToListTestimonialDtoFull(List<TestimonialEntity>list);

    TestimonialDtoFull testimonialToTestimonialDtoFull(TestimonialEntity testimonialEntity);
}
