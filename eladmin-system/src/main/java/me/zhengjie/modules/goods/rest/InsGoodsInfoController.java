package me.zhengjie.modules.goods.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.goods.domain.InsGoodsInfo;
import me.zhengjie.modules.goods.service.InsGoodsInfoService;
import me.zhengjie.modules.goods.service.dto.InsGoodsInfoQueryCriteria;
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
* @author tianying
* @date 2020-01-31
*/
@Api(tags = "商品接口管理")
@RestController
@RequestMapping("/api/insGoodsInfo")
public class InsGoodsInfoController {

    private final InsGoodsInfoService insGoodsInfoService;

    public InsGoodsInfoController(InsGoodsInfoService insGoodsInfoService) {
        this.insGoodsInfoService = insGoodsInfoService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('insGoodsInfo:list')")
    public void download(HttpServletResponse response, InsGoodsInfoQueryCriteria criteria) throws IOException {
        insGoodsInfoService.download(insGoodsInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询商品接口")
    @ApiOperation("查询商品接口")
    @PreAuthorize("@el.check('insGoodsInfo:list')")
    public ResponseEntity<Object> getInsGoodsInfos(InsGoodsInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(insGoodsInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增商品接口")
    @ApiOperation("新增商品接口")
    @PreAuthorize("@el.check('insGoodsInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody InsGoodsInfo resources){
        return new ResponseEntity<>(insGoodsInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改商品接口")
    @ApiOperation("修改商品接口")
    @PreAuthorize("@el.check('insGoodsInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody InsGoodsInfo resources){
        insGoodsInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除商品接口")
    @ApiOperation("删除商品接口")
    @PreAuthorize("@el.check('insGoodsInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody String[] ids) {
        insGoodsInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}