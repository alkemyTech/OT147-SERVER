package com.alkemy.ong.util;

import com.alkemy.ong.entity.ContactEntity;
import com.alkemy.ong.entity.MemberEntity;

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

}
