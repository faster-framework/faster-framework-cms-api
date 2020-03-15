package cn.org.faster.cms.common.directive.impl;

import cn.org.faster.cms.api.dict.service.DictService;
import cn.org.faster.cms.common.directive.BaseDirective;
import cn.org.faster.cms.common.directive.context.TagContext;
import cn.org.faster.framework.dict.entity.SysDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("dict")
public class DictDirective extends BaseDirective {
    @Autowired
    private DictService dictService;


    @Override
    public Object buildData(TagContext tagContext) {
        SysDict dictQuery = new SysDict();
        dictQuery.setType(tagContext.getType());
        dictQuery.setName(tagContext.getName());
        dictQuery.setId(tagContext.getId());
        dictQuery.setSize(tagContext.getSize());
        dictQuery.setCurrent(tagContext.getCurrent());

        if (tagContext.isList()) {
            //如果是列表，默认情况下获取全部
            return dictService.page(dictQuery).getRecords();
        } else if (tagContext.isPage()) {
            return dictService.page(dictQuery).getRecords();
        } else {
            return dictService.query(dictQuery);
        }
    }
}
