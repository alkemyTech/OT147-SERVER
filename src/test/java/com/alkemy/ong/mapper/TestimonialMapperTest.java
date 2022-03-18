package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.dto.TestimonialDtoFull;
import com.alkemy.ong.entity.TestimonialEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestimonialMapperTest {

    private static TestimonialMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper= Mappers.getMapper(TestimonialMapper.class);

    }
    @Test
    void testimonialDtoToTestimonialEntity() {
        TestimonialDto dto= new TestimonialDto();
        dto.setImage("img.jpg ");
        dto.setName("Name Testimony");
        dto.setContent("Content");
        TestimonialEntity ent= mapper.testimonialDtoToTestimonialEntity(dto);
        System.out.println(ent.getName());
        assertAll(
                ()->{
                    assertEquals(dto.getName(),ent.getName());
                    assertEquals(dto.getImage(), ent.getImage());
                    assertEquals("Content",ent.getContent());
                }
        );
    }
    @Test
    public void testimonyDtoMapperTestSimpleNull(){
        TestimonialDto dto=null;
        TestimonialEntity entity=mapper.testimonialDtoToTestimonialEntity(dto);
        assertEquals(null,entity);
    }

    @Test
    void testimonyEntityToTestimonyDto() {
        TestimonialEntity testimonial= new TestimonialEntity();
        testimonial.setId("1234");
        testimonial.setImage("testimonial.jpg");
        testimonial.setName("name testimonial");
        testimonial.setSoftDelete(false);
        testimonial.setContent("Content");
        testimonial.setTimestamps(LocalDateTime.now());

        TestimonialDto dto=mapper.testimonialToTestimonialDto(testimonial);
        System.out.println(dto.getName());
        assertAll(
                ()->{
                    assertEquals(dto.getName(),testimonial.getName());
                    assertEquals(dto.getImage(), testimonial.getImage());
                    assertEquals("Content",testimonial.getContent());
                }
        );
    }
    @Test
    public void testimonialMapperTestSimpleNull(){
        TestimonialEntity entity=null;
        TestimonialDto dto=mapper.testimonialToTestimonialDto(entity);
        assertEquals(null,dto);
    }
    @Test
    public void testimonialListMapperTestSimpleNull(){
        List<TestimonialEntity> testimonialEntityList=null;
        List<TestimonialDtoFull>categoryDtoList=mapper.listTestimonialEntityToListTestimonialDtoFull(testimonialEntityList);
        assertEquals(null,categoryDtoList);
    }

    @Test
    void listTestimonialEntityToListTestimonialDtoFull() {

        TestimonialEntity testimonyList= new TestimonialEntity();
        testimonyList.setId("123456");
        testimonyList.setImage("Img.jpg");
        testimonyList.setName("List testimony");
        testimonyList.setSoftDelete(false);
        testimonyList.setContent("Content");
        testimonyList.setTimestamps(LocalDateTime.now());
        List<TestimonialEntity>testimonialEntityList=new ArrayList<>(Arrays.asList(testimonyList));
        List<TestimonialDtoFull>testimonialDtoList=mapper.listTestimonialEntityToListTestimonialDtoFull(testimonialEntityList);

        System.out.println(testimonialDtoList.get(0).getName());
        assertAll(
                ()->{
                    assertEquals(testimonialDtoList.get(0).getName(),testimonialEntityList.get(0).getName());
                    assertEquals(testimonialDtoList.get(0).getImage(),testimonialEntityList.get(0).getImage());
                    assertEquals("Content",testimonialDtoList.get(0).getContent());
                }
        );
    }

    @Test
    void testimonyToTestimonialDtoFull() {
        TestimonialEntity testimony= new TestimonialEntity();
        testimony.setId("1234567");
        testimony.setImage("img.jpg");
        testimony.setName("Name Testimony");
        testimony.setSoftDelete(false);
        testimony.setContent("Content");
        testimony.setTimestamps(LocalDateTime.now());

        TestimonialDtoFull dtoFull=mapper.testimonialToTestimonialDtoFull(testimony);
        assertAll(
                ()->{
                    assertEquals(dtoFull.getName(),testimony.getName());
                    assertEquals(dtoFull.getId(), testimony.getId());
                    assertEquals("Content",testimony.getContent());
                }
        );
    }

    @Test
    public void testimonyMapperToTestimonialDtoFullTestSimpleNull(){
        TestimonialEntity testimonyNull=null;
        TestimonialDtoFull dtoFull=mapper.testimonialToTestimonialDtoFull(testimonyNull);
        assertEquals(null,testimonyNull);
    }

}