package com.menu.controller;

import com.menu.pojo.CustomPageDto;
import com.menu.pojo.Result;
import com.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜谱核心")
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 搜     * @param customPageDto
     * @return
     */
    @ApiOperation("菜谱搜索")
    @PostMapping("/search")
    public Result search(@RequestBody CustomPageDto customPageDto){

        String keyword = (String) customPageDto.getQuery().get("keyword");
        if (StringUtils.isEmpty(keyword)){
            return Result.error("搜索词不能为空");
        }
        return menuService.search(customPageDto);
    }

    @ApiOperation("菜谱分类")
    @PostMapping("/classification")
    public Result classification(){
        return menuService.classification();
    }
    @ApiOperation("根据菜谱分类id查询分类菜谱")
    @PostMapping("/classSearch")
    public Result classSearch(@RequestBody CustomPageDto customPageDto){
        Integer classId = (Integer) customPageDto.getQuery().get("classId");
        if (classId == null){
            return Result.error("classId不能为空");
        }
        return menuService.classSearch(customPageDto);
    }
    @ApiOperation("根据菜谱分类id查询分类菜谱（简版）")
    @PostMapping("/simpleClassSearch")
    public Result simpleClassSearch(@RequestBody CustomPageDto customPageDto){
        Integer classId = (Integer) customPageDto.getQuery().get("classId");
        if (classId == null){
            return Result.error("classId不能为空");
        }
        return menuService.simpleClassSearch(customPageDto);
    }

    @ApiOperation("根据菜谱id查询菜谱详情")
    @PostMapping("/getById/{id}")
    public Result getById(@PathVariable Integer id){
        if (id == null){
            return Result.error("Id不能为空");
        }
        return menuService.getById(id);
    }

    @ApiOperation("随机n道菜")
    @PostMapping("/getRand/{n}")
    public Result getRand(@PathVariable("n") Integer num){
        if (num == null){
            return Result.error("随机数不能为空");
        }
        return menuService.getRand(num);
    }

}
