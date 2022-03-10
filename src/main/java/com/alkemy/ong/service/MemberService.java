package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.entity.MemberEntity;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;



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
   // Get all Members from Database.
    public List<MemberDto> getAllMembers() {
        List<MemberEntity> membersList = (List<MemberEntity>) memberRepository.findAll();
        return memberMapper.listMemberEntityToListMemberDto(membersList);
    }
    //Get all Members by 10 pages
    public Page<MemberEntity> findAllMembers(Pageable pageable) {
       return memberRepository.findAll(pageable);
    }
}
