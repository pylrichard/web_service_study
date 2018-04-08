package com.bd.imooc.permission.rbac.controller;

import com.bd.imooc.permission.rbac.common.JsonData;
import com.bd.imooc.permission.rbac.dto.DeptLevelDto;
import com.bd.imooc.permission.rbac.param.DeptParam;
import com.bd.imooc.permission.rbac.service.SysDeptService;
import com.bd.imooc.permission.rbac.service.SysTreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysTreeService sysTreeService;

    @GetMapping("/dept.page")
    public ModelAndView page() {
        //见views/dept.jsp
        return new ModelAndView("dept");
    }

    @GetMapping("/save.json")
    public JsonData saveDept(DeptParam param) {
        sysDeptService.saveDept(param);

        return JsonData.success();
    }

    @GetMapping("/tree.json")
    public JsonData createDeptTree() {
        List<DeptLevelDto> dtoList = sysTreeService.createDeptTree();

        return JsonData.success(dtoList);
    }

    @GetMapping("/update.json")
    public JsonData updateDept(DeptParam param) {
        sysDeptService.updateDept(param);

        return JsonData.success();
    }

    @GetMapping("/delete.json")
    public JsonData deleteDept(@RequestParam("id") int id) {
        sysDeptService.deleteDept(id);

        return JsonData.success();
    }
}
