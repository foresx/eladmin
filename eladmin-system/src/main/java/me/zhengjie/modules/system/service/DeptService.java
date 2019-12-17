package me.zhengjie.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.dto.DeptDTO;
import me.zhengjie.modules.system.service.dto.DeptQueryCriteria;

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
