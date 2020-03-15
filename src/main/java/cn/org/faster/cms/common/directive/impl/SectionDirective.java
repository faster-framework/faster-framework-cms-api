package cn.org.faster.cms.common.directive.impl;

import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
import cn.org.faster.cms.common.directive.BaseDirective;
import cn.org.faster.cms.common.directive.context.TagContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("section")
public class SectionDirective extends BaseDirective {
    @Autowired
    private SectionService sectionService;

    @Override
    public Object buildData(TagContext tagContext) {
        Section sectionQuery = new Section();
        sectionQuery.setParentId(tagContext.getPid());
        sectionQuery.setCode(tagContext.getCode());
        sectionQuery.setId(tagContext.getId());
        sectionQuery.setShowStatus(tagContext.isShow() ? 1 : 0);
        sectionQuery.setTopStatus(tagContext.getTop() == null ? null : tagContext.getTop() ? 1 : 0);
        sectionQuery.setPublishStatus(1);
        sectionQuery.setSize(tagContext.getSize());
        sectionQuery.setCurrent(tagContext.getCurrent());

        if (tagContext.isList()) {
            //如果是列表，默认情况下获取全部
            return sectionService.page(sectionQuery).getRecords();
        } else if (tagContext.isPage()) {
            return sectionService.page(sectionQuery).getRecords();
        } else {
            return sectionService.query(sectionQuery);
        }
    }
}
