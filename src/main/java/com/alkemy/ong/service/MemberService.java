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

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberMapper memberMapper;
    @Autowired
    private MemberRepository memberRepository;
    //Member creation method

    public MemberDto addMember(MemberDto memberDto) {
        try{
        MemberEntity memberEntity = memberMapper.memberDtoToMemberEntity(memberDto);
        MemberEntity savedMemberEntity = memberRepository.save(memberEntity);
        return memberMapper.memberEntityToMemberDto(savedMemberEntity);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
}
}
