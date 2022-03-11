package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.entity.MemberEntity;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberMapper memberMapper;
    @Autowired
    private MemberRepository memberRepository;
    //Member creation method

    public MemberDto addMember(MemberDto memberDto) {
        try {
            MemberEntity memberEntity = memberMapper.memberDtoToMemberEntity(memberDto);
            MemberEntity savedMemberEntity = memberRepository.save(memberEntity);
            return memberMapper.memberEntityToMemberDto(savedMemberEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //Get all Members from Database.
    public List<MemberDto> getAllMembers() {
        List<MemberEntity> membersList = memberRepository.findAll();
        return memberMapper.listMemberEntityToListMemberDto(membersList);
    }

    //delete member by Id
    public void deleteMemberById(String id) {
        MemberEntity member = memberRepository.findById(id).get();
        memberRepository.delete(member);
    }

    public MemberDto update(String id, MemberDto memberDto) {
        Optional<MemberEntity> entity = memberRepository.findById(id);
        if (entity.isPresent()) {
            MemberEntity memberEntity = entity.get();
            memberEntity.setName(memberDto.getName());
            memberEntity.setFacebookUrl(memberDto.getFacebookUrl());
            memberEntity.setInstagramUrl(memberDto.getInstagramUrl());
            memberEntity.setLinkedinUrl(memberDto.getLinkedinUrl());
            memberEntity.setImage(memberDto.getImage());
            memberEntity.setDescription(memberDto.getDescription());
            memberRepository.save(memberEntity);
            return memberMapper.memberEntityToMemberDto(memberEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Member with the entered Id");
        }
    }
}
