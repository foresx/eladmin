package com.castlery.modules.system.rest;

import com.castlery.aop.log.Log;
import com.castlery.system.service.DataScope;
import com.castlery.exception.BadRequestException;
import com.castlery.system.domain.Dept;
import com.castlery.system.service.DeptService;
import com.castlery.system.service.dto.DeptDTO;
import com.castlery.system.service.dto.DeptQueryCriteria;
import com.castlery.utils.ThrowableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zheng Jie
 * @date 2019-03-25
 */
@RestController
@Api(tags = "系统：部门管理")
@RequestMapping("/api/dept")
public class DeptController {

  private final DeptService deptService;

  private final DataScope dataScope;

  private static final String ENTITY_NAME = "dept";

  public DeptController(DeptService deptService, DataScope dataScope) {
    this.deptService = deptService;
    this.dataScope = dataScope;
  }

  @Log("导出部门数据")
  @ApiOperation("导出部门数据")
  @GetMapping(value = "/download")
  public void download(HttpServletResponse response, DeptQueryCriteria criteria)
      throws IOException {
    deptService.download(deptService.queryAll(criteria), response);
  }

  @Log("查询部门")
  @ApiOperation("查询部门")
  @GetMapping
  public ResponseEntity getDepts(DeptQueryCriteria criteria) {
    // 数据权限
    //        criteria.setIds(dataScope.getDeptIds());
    List<DeptDTO> deptDTOS = deptService.queryAll(criteria);
    //    List<DeptDTO> deptDTOS = deptService.queryAll();
    return new ResponseEntity<>(deptService.buildTree(deptDTOS), HttpStatus.OK);
  }

  @Log("新增部门")
  @ApiOperation("新增部门")
  @PostMapping
  public ResponseEntity create(@Validated @RequestBody Dept resources) {
    if (resources.getId() != null) {
      throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
    }
    return new ResponseEntity<>(deptService.create(resources), HttpStatus.CREATED);
  }

  @Log("修改部门")
  @ApiOperation("修改部门")
  @PutMapping
  public ResponseEntity update(@Validated(Dept.Update.class) @RequestBody Dept resources) {
    deptService.update(resources);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @Log("删除部门")
  @ApiOperation("删除部门")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    try {
      deptService.delete(id);
    } catch (Throwable e) {
      ThrowableUtil.throwForeignKeyException(
          e,
          "This department has connection with some user, you should revoke that before you delete.");
    }
    return new ResponseEntity(HttpStatus.OK);
  }
}
