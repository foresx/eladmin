package com.castlery.system.service.impl;

import com.castlery.exception.BadRequestException;
import com.castlery.system.domain.Dept;
import com.castlery.system.repository.DeptRepository;
import com.castlery.system.service.DeptService;
import com.castlery.system.service.dto.DeptDTO;
import com.castlery.system.service.dto.DeptQueryCriteria;
import com.castlery.system.service.mapper.DeptMapper;
import com.castlery.utils.FileUtil;
import com.castlery.utils.QueryHelp;
import com.castlery.utils.ValidationUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author Zheng Jie
 * @date 2019-03-25
 */
@Service
@CacheConfig(cacheNames = "dept")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl implements DeptService {

  private final DeptRepository deptRepository;

  private final DeptMapper deptMapper;

  public DeptServiceImpl(DeptRepository deptRepository, DeptMapper deptMapper) {
    this.deptRepository = deptRepository;
    this.deptMapper = deptMapper;
  }

  @Override
  @Cacheable
  public List<DeptDTO> queryAll(DeptQueryCriteria criteria) {
    return deptMapper.toDto(
        deptRepository.findAll(
            (root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
  }

  @Override
  public List<DeptDTO> queryAll() {
    List<Dept> all = deptRepository.findAll();
    return deptMapper.toDto(all);
  }

  @Override
  @Cacheable(key = "#p0")
  public DeptDTO findById(Long id) {
    Dept dept = deptRepository.findById(id).orElseGet(Dept::new);
    ValidationUtil.isNull(dept.getId(), "Dept", "id", id);
    return deptMapper.toDto(dept);
  }

  @Override
  @Cacheable
  public List<Dept> findByPid(long pid) {
    return deptRepository.findByPid(pid);
  }

  @Override
  public Set<Dept> findByRoleIds(Long id) {
    return deptRepository.findByRoles_Id(id);
  }

  @Override
  @Cacheable
  public Object buildTree(List<DeptDTO> deptDTOS) {
    Set<DeptDTO> trees = new LinkedHashSet<>();
    Set<DeptDTO> depts = new LinkedHashSet<>();
    List<String> deptNames = deptDTOS.stream().map(DeptDTO::getName).collect(Collectors.toList());
    boolean isChild;
    for (DeptDTO deptDTO : deptDTOS) {
      isChild = false;
      if ("0".equals(deptDTO.getPid().toString())) {
        trees.add(deptDTO);
      }
      for (DeptDTO it : deptDTOS) {
        if (it.getPid().equals(deptDTO.getId())) {
          isChild = true;
          if (deptDTO.getChildren() == null) {
            deptDTO.setChildren(new ArrayList<>());
          }
          deptDTO.getChildren().add(it);
        }
      }
      if (isChild) {
        depts.add(deptDTO);
      } else if (!deptNames.contains(deptRepository.findNameById(deptDTO.getPid()))) {
        depts.add(deptDTO);
      }
    }

    if (CollectionUtils.isEmpty(trees)) {
      trees = depts;
    }

    Integer totalElements = deptDTOS.size();

    Map<String, Object> map = new HashMap<>();
    map.put("totalElements", totalElements);
    map.put("content", CollectionUtils.isEmpty(trees) ? deptDTOS : trees);
    return map;
  }

  @Override
  @CacheEvict(allEntries = true)
  @Transactional(rollbackFor = Exception.class)
  public DeptDTO create(Dept resources) {
    return deptMapper.toDto(deptRepository.save(resources));
  }

  @Override
  @CacheEvict(allEntries = true)
  @Transactional(rollbackFor = Exception.class)
  public void update(Dept resources) {
    if (resources.getId().equals(resources.getPid())) {
      throw new BadRequestException("上级不能为自己");
    }
    Dept dept = deptRepository.findById(resources.getId()).orElseGet(Dept::new);
    ValidationUtil.isNull(dept.getId(), "Dept", "id", resources.getId());
    resources.setId(dept.getId());
    deptRepository.save(resources);
  }

  @Override
  @CacheEvict(allEntries = true)
  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) {
    deptRepository.deleteById(id);
  }

  @Override
  public void download(List<DeptDTO> deptDTOs, HttpServletResponse response) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (DeptDTO deptDTO : deptDTOs) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("部门名称", deptDTO.getName());
      map.put("部门状态", deptDTO.getEnabled() ? "启用" : "停用");
      map.put("创建日期", deptDTO.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }
}
