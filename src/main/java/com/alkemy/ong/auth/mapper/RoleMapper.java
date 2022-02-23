package com.alkemy.ong.auth.mapper;

import com.alkemy.ong.auth.domain.RoleDomain;
import com.alkemy.ong.entity.RoleEntity;

public class RoleMapper {

    public static RoleDomain roleEntityToRoleDomain(RoleEntity roleEntity) {
        RoleDomain roleDomain = RoleDomain.builder().
                id(roleEntity.getId()).
                name(roleEntity.getName()).
                description(roleEntity.getDescription()).
                timestamps(roleEntity.getTimestamps()).build();
        return roleDomain;
    }

    public static RoleEntity roleDomainToRoleEntity(RoleDomain roleDomain) {
        RoleEntity roleEntity = RoleEntity.builder().
                id(roleDomain.getId()).
                name(roleDomain.getName()).
                description(roleDomain.getDescription()).
                timestamps(roleDomain.getTimestamps()).build();
        return roleEntity;
    }
}
