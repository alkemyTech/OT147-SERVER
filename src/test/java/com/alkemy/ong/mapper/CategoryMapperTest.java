package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull;
import com.alkemy.ong.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {
    private static CategoryMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper= Mappers.getMapper(CategoryMapper.class);

    }
    @Test
    void categoryDtoToCategoryEntity() {
        CategoryDto dto= new CategoryDto();
        dto.setImage("IMAGE ");
        dto.setName("NAME category");
        dto.setDescription("Description123");
        CategoryEntity ent= mapper.categoryDtoToCategoryEntity(dto);
        assertAll(
                ()->{
                    assertEquals(dto.getName(),ent.getName());
                    assertEquals(dto.getImage(), ent.getImage());
                    assertEquals("Description123",ent.getDescription());
                }
        );
    }
    @Test
    public void categoryDtoMapperTestSimpleNull(){
        CategoryDto dto=null;
        CategoryEntity entity=mapper.categoryDtoToCategoryEntity(dto);
        assertEquals(null,entity);
    }

    @Test
    void categoryEntityToCategoryDto() {
        CategoryEntity category= new CategoryEntity();
        category.setId("1234");
        category.setImage("Imaggen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        category.setTimestamps(LocalDateTime.now());

        CategoryDto dto=mapper.categoryEntityToCategoryDto(category);
        assertAll(
                ()->{
                    assertEquals(dto.getName(),category.getName());
                    assertEquals(dto.getImage(), category.getImage());
                    assertEquals("Description",category.getDescription());
                }
        );
    }
    @Test
    public void categoryMapperTestSimpleNull(){
        CategoryEntity entity=null;
        CategoryDto dto=mapper.categoryEntityToCategoryDto(entity);
        assertEquals(null,dto);
    }
    @Test
    public void categoryListMapperTestSimpleNull(){
        List<CategoryEntity> categoryEntityList=null;
        List<CategoryDto>categoryDtoList=mapper.listCategoryEntityToListCategoryDto(categoryEntityList);
        assertEquals(null,categoryDtoList);
    }

    @Test
    void listCategoryEntityToListCategoryDto() {

        CategoryEntity categoryL= new CategoryEntity();
        categoryL.setId("123456");
        categoryL.setImage("Imaggen category");
        categoryL.setName("List category");
        categoryL.setSoftDelete(false);
        categoryL.setDescription("Es una Descripcion");
        categoryL.setTimestamps(LocalDateTime.now());
        List<CategoryEntity>categoryEntityList1=new ArrayList<>(Arrays.asList(categoryL));
        List<CategoryDto>categoryDtoList=mapper.listCategoryEntityToListCategoryDto(categoryEntityList1);

        assertAll(
                ()->{
                    assertEquals(categoryDtoList.get(0).getName(),categoryEntityList1.get(0).getName());
                    assertEquals(categoryDtoList.get(0).getImage(),categoryEntityList1.get(0).getImage());
                    assertEquals("Es una Descripcion",categoryDtoList.get(0).getDescription());
                }
        );
    }

    @Test
    void categoryToCategoryDtoFull() {
        CategoryEntity cat= new CategoryEntity();
        cat.setId("1234567");
        cat.setImage("IMG");
        cat.setName("NAME");
        cat.setSoftDelete(false);
        cat.setDescription("Description");
        cat.setTimestamps(LocalDateTime.now());

        CategoryDtoFull dtoFull=mapper.categoryToCategoryDtoFull(cat);
        assertAll(
                ()->{
                    assertEquals(dtoFull.getName(),cat.getName());
                    assertEquals(dtoFull.getId(), cat.getId());
                    assertEquals("Description",cat.getDescription());
                }
        );
    }

    @Test
    public void categoryDtoFullMapperTestSimpleNull(){
        CategoryDtoFull dtoFull=null;
        CategoryEntity entity=mapper.categoryFullDtoToCategoryEntity(dtoFull);
        assertEquals(null,entity);
    }
    @Test
    public void categoryMapperToCategoryFullDtoTestSimpleNull(){
        CategoryEntity entityNull=null;
        CategoryDtoFull categoryDtoFull= mapper.categoryToCategoryDtoFull(entityNull);
        assertEquals(null,categoryDtoFull);
    }
    @Test
    void categoryFullDtoToCategoryEntity() {
        CategoryDtoFull catDtoFull= new CategoryDtoFull();
        catDtoFull.setId("0123");
        catDtoFull.setImage("IMaGe");
        catDtoFull.setName("NaMe");
        catDtoFull.setSoftDelete(false);
        catDtoFull.setDescription("DESCRIPTION");
        catDtoFull.setTimestamps(LocalDateTime.now());

        CategoryEntity entityFull=mapper.categoryFullDtoToCategoryEntity(catDtoFull);
        assertAll(
                ()->{
                    assertEquals(entityFull.getName(),catDtoFull.getName());
                    assertEquals(entityFull.getId(), catDtoFull.getId());
                    assertEquals("DESCRIPTION",entityFull.getDescription());
                }
        );
    }
}