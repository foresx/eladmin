package com.castlery.system.service;

import com.castlery.system.domain.User;
import com.castlery.system.service.dto.UserDTO;
import com.castlery.system.service.dto.UserQueryCriteria;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
public interface UserService {

  UserDTO findById(long id);

  UserDTO create(User resources);

  User createOauth2User(String email);

  void update(User resources);

  void delete(Long id);

  UserDTO findByName(String userName);

  void updatePass(String username, String encryptPassword);

  //    void updateAvatar(MultipartFile file);

  void updateEmail(String username, String email);

  Object queryAll(UserQueryCriteria criteria, Pageable pageable);

  List<UserDTO> queryAll(UserQueryCriteria criteria);

  void download(List<UserDTO> queryAll, HttpServletResponse response) throws IOException;
}
