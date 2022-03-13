package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.entity.MemberEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private static final int PAGE_SIZE = 10;
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
    //delete member by Id
        public void deleteMemberById (String id){
            MemberEntity member = memberRepository.findById(id).get();
            memberRepository.delete(member);

        }
        //Get all members paginated by User
     public PagesDto<MemberDto> searchPaginatedMembers(int page) {
            if (page < 0) {
                throw new ParamNotFound("The was an error retrieving the list of members");
            }
            Pageable pageRequest = PageRequest.of(page, PAGE_SIZE);
            Page<MemberEntity> membersList = memberRepository.findAll(pageRequest);
            return responsePage(membersList);
        }
    private PagesDto<MemberDto> responsePage(Page<MemberEntity> page) {
        if (page.isEmpty()) {
            throw new ParamNotFound("The page does not exist.");
        }
        Page<MemberDto> response = new PageImpl<>(
                memberMapper.listMemberEntityToListMemberDto(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize()),
                page.getTotalElements());
        return new PagesDto<>(response, "localhost:8080/members?page=");
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
            throw new ParamNotFound("There is no Member with the entered Id");
        }
    }
}

