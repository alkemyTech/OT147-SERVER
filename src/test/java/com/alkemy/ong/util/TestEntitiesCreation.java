package com.alkemy.ong.util;

import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.entity.ContactEntity;
import com.alkemy.ong.entity.MemberEntity;
import com.alkemy.ong.entity.NewsEntity;

public class TestEntitiesCreation {

    //ContactEntity
    public ContactEntity exampleContactEntity() {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId("1f99ge0e");
        contactEntity.setName("NameExample");
        contactEntity.setPhone("011-555-4568");
        contactEntity.setEmail("example@email.com");
        contactEntity.setMessage("exampleMessage");
        contactEntity.setDeletedAt(false);
        return contactEntity;
    }

    //memberEntity
    public MemberEntity exampleMemberEntity() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId("1f35fe0e");
        memberEntity.setName("Name");
        memberEntity.setFacebookUrl("facebookUrl");
        memberEntity.setInstagramUrl("instagramUrl");
        memberEntity.setLinkedinUrl("linkedinUrl");
        memberEntity.setImage("imageDaniel");
        memberEntity.setDescription("description");
        memberEntity.setSoftDelete(false);
        return memberEntity;
    }
    public NewsEntity createNewsEntity(){
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        NewsEntity news = new NewsEntity();
        news.setId("123e4567-e89b-12d3-a456-426614174200");
        news.setName("Novedad");
        news.setContent("Contenido");
        news.setImage("Imagen");
        news.setCategoryId(category);
        news.setSoftDelete(false);
        return news;
    }
    public NewsEntity createNewsEntity2(){
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category2");
        category.setName("nombre category2");
        category.setSoftDelete(false);
        category.setDescription("Description2");
        NewsEntity news = new NewsEntity();
        news.setId("123e4567-e89b-12d3-a456-426614174000");
        news.setName("Novedad2");
        news.setContent("Contenido2");
        news.setImage("Imagen2");
        news.setCategoryId(category);
        news.setSoftDelete(false);
        return news;
    }

}
