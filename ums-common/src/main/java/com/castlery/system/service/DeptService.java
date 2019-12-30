package com.castlery.system.service;

import com.castlery.system.domain.Dept;
import com.castlery.system.service.dto.DeptDTO;
import com.castlery.system.service.dto.DeptQueryCriteria;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2019-03-25
 */
public interface DeptService {

  List<DeptDTO> queryAll(DeptQueryCriteria criteria);

  List<DeptDTO> queryAll();

  DeptDTO findById(Long id);

  DeptDTO create(Dept resources);

  void update(Dept resources);

  void delete(Long id);

  Object buildTree(List<DeptDTO> deptDTOS);

  List<Dept> findByPid(long pid);

  Set<Dept> findByRoleIds(Long id);

  void download(List<DeptDTO> queryAll, HttpServletResponse response) throws IOException;
}
