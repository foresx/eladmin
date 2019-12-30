package com.castlery.system.service.mapper;

import com.castlery.base.BaseMapper;
import com.castlery.system.domain.User;
import com.castlery.system.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Mapper(
    componentModel = "spring",
    uses = {RoleMapper.class, DeptMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDTO, User> {

  UserDTO toDto(User user);
}
