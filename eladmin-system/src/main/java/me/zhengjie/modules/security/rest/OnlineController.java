// package me.zhengjie.modules.security.rest;
//
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import java.io.IOException;
// import javax.servlet.http.HttpServletResponse;
// import me.zhengjie.aop.log.Log;
// import me.zhengjie.modules.security.service.OnlineUserService;
// import org.springframework.data.domain.Pageable;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// @RestController
// @RequestMapping("/auth/online")
// @Api(tags = "系统：在线用户管理")
// public class OnlineController {
//
//  private final OnlineUserService onlineUserService;
//
//  public OnlineController(OnlineUserService onlineUserService) {
//    this.onlineUserService = onlineUserService;
//  }
//
//  @ApiOperation("查询在线用户")
//  @GetMapping
//  public ResponseEntity getAll(String filter, Pageable pageable) {
//    return new ResponseEntity<>(onlineUserService.getAll(filter, pageable), HttpStatus.OK);
//  }
//
//  @Log("导出数据")
//  @ApiOperation("导出数据")
//  @GetMapping(value = "/download")
//  public void download(HttpServletResponse response, String filter) throws IOException {
//    onlineUserService.download(onlineUserService.getAll(filter), response);
//  }
//
//  @ApiOperation("踢出用户")
//  @DeleteMapping(value = "/{key}")
//  public ResponseEntity delete(@PathVariable String key) throws Exception {
//    onlineUserService.kickOut(key);
//    return new ResponseEntity(HttpStatus.OK);
//  }
// }
