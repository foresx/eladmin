package com.castlery.system.service.mapper;

import com.castlery.base.BaseMapper;
import com.castlery.system.domain.Role;
import com.castlery.system.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Mapper(
    componentModel = "spring",
    uses = {MenuMapper.class, DeptMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDTO, Role> {

}
