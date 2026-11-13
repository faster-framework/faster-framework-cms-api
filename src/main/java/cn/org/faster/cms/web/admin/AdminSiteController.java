package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.service.SiteGenerationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 站点维护接口。
 */
@RestController
@RequestMapping("/admin/site")
public class AdminSiteController {

    private final SiteGenerationService siteGenerationService;

    public AdminSiteController(SiteGenerationService siteGenerationService) {
        this.siteGenerationService = siteGenerationService;
    }

    /**
     * 全站静态化，返回生成统计与失败明细。
     */
    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('site:manage')")
    public SiteGenerationService.GenerationReport generate() {
        return siteGenerationService.generate();
    }
}
