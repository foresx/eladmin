package com.castlery.modules.system.service.mapper;

import com.castlery.base.BaseMapper;
import com.castlery.modules.system.domain.User;
import com.castlery.modules.system.service.dto.UserDTO;
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
