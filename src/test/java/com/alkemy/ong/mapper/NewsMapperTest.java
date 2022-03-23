package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.mapper.NewsMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewsMapperTest {

    private static NewsMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = Mappers.getMapper(NewsMapper.class);

    }
    @Test
    void newsEntityToNewsDto() {
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        category.setTimestamps(LocalDateTime.now());
        NewsEntity news = new NewsEntity();
        news.setId("123e4567-e89b-12d3-a456-426614174000");
        news.setName("Novedad");
        news.setContent("Contenido");
        news.setImage("Imagen");
        news.setTimestamps(LocalDateTime.now());
        news.setCategoryId(category);
        news.setSoftDelete(false);
        NewsDto newsDto = mapper.newsEntityToNewsDto(news);
        assertAll(
                () -> {
                    assertEquals(newsDto.getId(), news.getId());
                    assertEquals(newsDto.getName(), news.getName());
                    assertEquals(newsDto.getContent(), news.getContent());
                    assertEquals(newsDto.getImage(), news.getImage());
                    assertEquals(newsDto.getTimestamps(), news.getTimestamps());
                    assertEquals(newsDto.getCategoryId(), news.getCategoryId());
                    assertEquals(newsDto.getSoftDelete(), news.getSoftDelete());
                }
        );
    }
    @Test
    public void newsEntityToNewsDtoSimpleNullTest(){
        NewsEntity news=null;
        NewsDto dto=mapper.newsEntityToNewsDto(news);
        assertEquals(null,news);
    }
    @Test
    void newsDtoToNewsEntityTest(){
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        category.setTimestamps(LocalDateTime.now());
        NewsDto newsDto = new NewsDto();
        newsDto.setId("123e4567-e89b-12d3-a456-426614174000");
        newsDto.setName("Novedad");
        newsDto.setContent("Contenido");
        newsDto.setImage("Imagen");
        newsDto.setTimestamps(LocalDateTime.now());
        newsDto.setCategoryId(category);
        newsDto.setSoftDelete(false);
        NewsEntity newsEntity = mapper.newsDtoTonewsEntity(newsDto);
        assertAll(
                () -> {
                    assertEquals(newsDto.getId(), newsEntity.getId());
                    assertEquals(newsDto.getName(), newsEntity.getName());
                    assertEquals(newsDto.getContent(), newsEntity.getContent());
                    assertEquals(newsDto.getImage(), newsEntity.getImage());
                    assertEquals(newsDto.getTimestamps(), newsEntity.getTimestamps());
                    assertEquals(newsDto.getCategoryId(), newsEntity.getCategoryId());
                    assertEquals(newsDto.getSoftDelete(), newsEntity.getSoftDelete());
                }
        );
    }
    @Test
    public void newsEntityDtoNewsEntitySimpleNullTest(){
        NewsDto dto=null;
        NewsEntity news=mapper.newsDtoTonewsEntity(dto);
        assertEquals(null,news);
    }
    @Test
    void listNewsEntityToNewsDtoTest(){
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        category.setTimestamps(LocalDateTime.now());
        NewsEntity news = new NewsEntity();
        news.setId("123e4567-e89b-12d3-a456-426614174000");
        news.setName("Novedad");
        news.setContent("Contenido");
        news.setImage("Imagen");
        news.setTimestamps(LocalDateTime.now());
        news.setCategoryId(category);
        news.setSoftDelete(false);
        CategoryEntity category1 = new CategoryEntity();
        category1.setId("1234");
        category1.setImage("Imagen category");
        category1.setName("nombre category");
        category1.setSoftDelete(false);
        category1.setDescription("Description");
        category1.setTimestamps(LocalDateTime.now());
        NewsEntity news1 = new NewsEntity();
        news1.setId("123e4567-e89b-12d3-a456-426614174000");
        news1.setName("Novedad");
        news1.setContent("Contenido");
        news1.setImage("Imagen");
        news1.setTimestamps(LocalDateTime.now());
        news1.setCategoryId(category1);
        news1.setSoftDelete(false);
        List<NewsEntity> newsList=new ArrayList<>(Arrays.asList(news,news1));
        List<NewsDto>newsDtoList=mapper.listNewsEntityToNewsDto(newsList);
        assertAll(
                () -> {
                    assertEquals(newsDtoList.get(0).getId(), newsList.get(0).getId());
                    assertEquals(newsDtoList.get(0).getName(), newsList.get(0).getName());
                    assertEquals(newsDtoList.get(0).getContent(), newsList.get(0).getContent());
                    assertEquals(newsDtoList.get(0).getImage(), newsList.get(0).getImage());
                    assertEquals(newsDtoList.get(0).getTimestamps(), newsList.get(0).getTimestamps());
                    assertEquals(newsDtoList.get(0).getCategoryId(), newsList.get(0).getCategoryId());
                    assertEquals(newsDtoList.get(0).getSoftDelete(), newsList.get(0).getSoftDelete());
                    assertEquals(newsDtoList.get(1).getId(), newsList.get(1).getId());
                    assertEquals(newsDtoList.get(1).getName(), newsList.get(1).getName());
                    assertEquals(newsDtoList.get(1).getContent(), newsList.get(1).getContent());
                    assertEquals(newsDtoList.get(1).getImage(), newsList.get(1).getImage());
                    assertEquals(newsDtoList.get(1).getTimestamps(), newsList.get(1).getTimestamps());
                    assertEquals(newsDtoList.get(1).getCategoryId(), newsList.get(1).getCategoryId());
                    assertEquals(newsDtoList.get(1).getSoftDelete(), newsList.get(1).getSoftDelete());
                }
        );
    }
    @Test
    void listNewsEntityToNewsDtoSimpleNullTest(){
        List<NewsEntity> newsList=null;
        List<NewsDto>newsDtoList=mapper.listNewsEntityToNewsDto(newsList);
        assertAll(
                () -> {
                    assertEquals(null, newsDtoList);
                       }
                );
    }

}


