package cn.org.faster.cms.view.category;

import cn.org.faster.cms.api.category.entity.Category;
import cn.org.faster.cms.api.category.service.CategoryService;
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
@RequestMapping("/c")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CmsProperties cmsProperties;

    /**
     * 根据分类code获取分页数据
     *
     * @param categoryCode
     * @param current
     * @param model
     * @return
     */
    @GetMapping({"/{categoryCode}/{current}", "/{categoryCode}"})
    public String list(@PathVariable String categoryCode, @PathVariable(required = false) String current, Model model) {
        Category query = new Category();
        query.setCode(categoryCode);
        Category category = categoryService.query(query);
        current = Optional.ofNullable(current).orElse("1");
        Map<String, Object> params = new HashMap<>();
        params.put("categoryCode", categoryCode);
        params.put("current", current);
        params.put("size", cmsProperties.getPageSize());
        model.addAttribute("page", params);
        return category.getTemplatePath();
    }
}
