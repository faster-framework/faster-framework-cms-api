package cn.org.faster.cms.view.index;

import cn.org.faster.cms.common.properties.CmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-03
 */
@Controller
@RequestMapping
public class IndexController implements ErrorController {
    @Autowired
    private CmsProperties cmsProperties;

    @GetMapping
    public String index(Model model) {
        Map<String, Object> params = new HashMap<>();
        model.addAttribute(cmsProperties.getContextPrefix(), params);
        return "/index.ftl";
    }

    @GetMapping("/redirectError")
    public String error() {
        return "redirect:/error";
    }

    @GetMapping("/error")
    public String notFound(Model model) {
        Map<String, Object> params = new HashMap<>();
        model.addAttribute(cmsProperties.getContextPrefix(), params);
        return "/error.ftl";
    }

    @Override
    public String getErrorPath() {
        return "/redirectError";
    }

}
