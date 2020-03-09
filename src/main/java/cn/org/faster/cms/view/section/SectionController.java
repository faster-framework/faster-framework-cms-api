package cn.org.faster.cms.view.section;

import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
import cn.org.faster.cms.common.config.CmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhangbowen
 * @since 2019-12-06
 */
@Controller
@RequestMapping("/s")
public class SectionController {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private CmsProperties cmsProperties;

    /**
     * 根据栏目code获取分页数据
     *
     * @param sectionCode
     * @param current
     * @param model
     * @return
     */
    @GetMapping({"/{sectionCode}/{current}", "/{sectionCode}"})
    public String list(@PathVariable String sectionCode, @PathVariable(required = false) String current, Model model) {
        Section query = new Section();
        query.setCode(sectionCode);
        Section section = sectionService.query(query);
        current = Optional.ofNullable(current).orElse("1");
        Map<String, Object> params = new HashMap<>();
        params.put("sectionCode", sectionCode);
        params.put("current", current);
        params.put("size", cmsProperties.getPageSize());
        model.addAttribute("page", params);
        return section.getTemplatePath();
    }
}
