package com.yzd.modules.study.rest;

import com.yzd.annotation.AnonymousAccess;
import com.yzd.aop.log.Log;
import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.service.SignHistoryService;
import com.yzd.modules.study.service.dto.SignHistoryQueryCriteria;
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
* @date 2020-05-05
*/
@Api(tags = "签到历史记录管理")
@RestController
@RequestMapping("/api/signHistory")
public class SignHistoryController {

    private final SignHistoryService signHistoryService;

    public SignHistoryController(SignHistoryService signHistoryService) {
        this.signHistoryService = signHistoryService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('signHistory:list')")
    public void download(HttpServletResponse response, SignHistoryQueryCriteria criteria) throws IOException {
        signHistoryService.download(signHistoryService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询签到历史记录")
    @ApiOperation("查询签到历史记录")
    @PreAuthorize("@el.check('signHistory:list')")
    public ResponseEntity<Object> getSignHistory(SignHistoryQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(signHistoryService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @PostMapping
    @Log("新增签到历史记录")
    @ApiOperation("新增签到历史记录")
    @PreAuthorize("@el.check('signHistory:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SignHistory resources){
        return new ResponseEntity<>(signHistoryService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改签到历史记录")
    @ApiOperation("修改签到历史记录")
    @PreAuthorize("@el.check('signHistory:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SignHistory resources){
        signHistoryService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除签到历史记录")
    @ApiOperation("删除签到历史记录")
    @PreAuthorize("@el.check('signHistory:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        signHistoryService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}