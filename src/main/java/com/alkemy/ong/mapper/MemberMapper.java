package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper memberMapper= Mappers.getMapper(MemberMapper.class);
    public MemberEntity memberDtoToMemberEntity(MemberDto memberDto);
    public MemberDto memberEntityToMemberDto(MemberEntity memberEntity);
}
