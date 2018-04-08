package com.bd.imooc.security.rbac.web.controller;

import com.bd.imooc.security.core.support.SimpleResponse;
import com.bd.imooc.security.rbac.domain.Admin;
import com.bd.imooc.security.rbac.dto.ResourceInfo;
import com.bd.imooc.security.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    /**
     * 获取资源树
     */
    @GetMapping
    public ResourceInfo getTree(@AuthenticationPrincipal Admin admin) {
        return resourceService.getTree(admin.getId());
    }

    /**
     * 获取资源信息
     */
    @GetMapping("/{id}")
    public ResourceInfo getInfo(@PathVariable Long id) {
        return resourceService.getInfo(id);
    }

    /**
     * 创建资源
     */
    @PostMapping
    public ResourceInfo create(@RequestBody ResourceInfo info) {
        if (info.getParentId() == null) {
            info.setParentId(0L);
        }
        return resourceService.create(info);
    }

    /**
     * 修改资源
     */
    @PutMapping("/{id}")
    public ResourceInfo update(@RequestBody ResourceInfo info) {
        return resourceService.update(info);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        resourceService.delete(id);
    }

    /**
     * 资源上移
     */
    @PostMapping("/{id}/up")
    public SimpleResponse moveUp(@PathVariable Long id) {
        return new SimpleResponse(resourceService.move(id, true));
    }

    /**
     * 资源下移
     */
    @PostMapping("/{id}/down")
    public SimpleResponse moveDown(@PathVariable Long id) {
        return new SimpleResponse(resourceService.move(id, false));
    }
}
