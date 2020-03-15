package cn.org.faster.cms.view.section;

import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
import cn.org.faster.cms.common.properties.CmsProperties;
import cn.org.faster.framework.web.exception.model.BasicErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

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
    public String list(@PathVariable String sectionCode, @PathVariable(required = false) Integer current, Model model) {
        Section query = new Section();
        query.setCode(sectionCode);
        query.setPublishStatus(1);
        Section section = sectionService.query(query);
        if (section == null || StringUtils.isEmpty(section.getTemplatePath())) {
            BasicErrorCode.ERROR.throwException();
        }
        Map<String, Object> params = sectionService.renderTemplate(section, current);
        model.addAttribute(cmsProperties.getContextPrefix(), params);
        return section.getTemplatePath();
    }
}
