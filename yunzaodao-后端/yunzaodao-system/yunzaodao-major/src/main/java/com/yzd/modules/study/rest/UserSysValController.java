package com.yzd.modules.study.rest;

import com.yzd.aop.log.Log;
import com.yzd.modules.study.domain.UserSysVal;
import com.yzd.modules.study.service.UserSysValService;
import com.yzd.modules.study.service.dto.UserSysValQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-05-13
*/
@Api(tags = "系统变量管理管理")
@RestController
@RequestMapping("/api/userSysVal")
public class UserSysValController {

    private final UserSysValService userSysValService;

    public UserSysValController(UserSysValService userSysValService) {
        this.userSysValService = userSysValService;
    }

//    @Log("导出数据")
//    @ApiOperation("导出数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('userSysVal:list')")
//    public void download(HttpServletResponse response, UserSysValQueryCriteria criteria) throws IOException {
//
//        userSysValService.download(userSysValService.queryAll(criteria), response);
//    }

    @GetMapping
    @Log("查询系统变量")
    @ApiOperation("查询系统变量")
    @PreAuthorize("@el.check('userSysVal:list')")
    public ResponseEntity<Object> getUserSysVals(Long userId){
        return new ResponseEntity<>(userSysValService.findByUser(userId),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增系统变量")
    @ApiOperation("新增系统变量")
    @PreAuthorize("@el.check('userSysVal:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody UserSysVal resources){
        return new ResponseEntity<>(userSysValService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改系统变量")
    @ApiOperation("修改系统变量")
    @PreAuthorize("@el.check('userSysVal:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody UserSysVal resources){
        userSysValService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除系统变量")
    @ApiOperation("删除系统变量")
    @PreAuthorize("@el.check('userSysVal:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        userSysValService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}