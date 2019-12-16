package cn.org.faster.cms.admin.site.controller;

import cn.org.faster.cms.admin.site.service.SiteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbowen
 * @since 2019-12-13
 */
@RestController
@RequestMapping("/admin/site")
public class SiteController {
    @Autowired
    private SiteService siteService;
    /**
     * @return 站点生成
     */
    @PostMapping("/generate")
    @RequiresPermissions("site:manage")
    public ResponseEntity generate() {
        return ResponseEntity.ok(siteService.generate());
    }
}
