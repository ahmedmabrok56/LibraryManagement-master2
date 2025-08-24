package com.dailycodework.lib.mapper;

import com.dailycodework.lib.dto.MemberDto;
import com.dailycodework.lib.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDto toDto(Member member) {
        if (member == null) return null;
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setMembershipNumber(member.getMembershipNumber());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setAddress(member.getAddress());
        dto.setDateOfBirth(member.getDateOfBirth());
        dto.setMembershipDate(member.getMembershipDate());
        dto.setMembershipExpiry(member.getMembershipExpiry());
        dto.setActive(member.isActive());
        return dto;
    }

    public Member toEntity(MemberDto dto) {
        if (dto == null) return null;
        Member member = new Member();
        member.setId(dto.getId());
        member.setMembershipNumber(dto.getMembershipNumber());
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());
        member.setAddress(dto.getAddress());
        member.setDateOfBirth(dto.getDateOfBirth());
        member.setMembershipDate(dto.getMembershipDate());
        member.setMembershipExpiry(dto.getMembershipExpiry());
        member.setActive(dto.isActive());
        return member;
    }
}
