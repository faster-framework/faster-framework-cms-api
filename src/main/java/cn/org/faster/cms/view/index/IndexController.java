package cn.org.faster.cms.view.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangbowen
 * @since 2019-12-03
 */
@Controller
@RequestMapping
public class IndexController {
    @GetMapping
    public String index(Model model) {
        return "/index.ftl";
    }
}
