package cn.org.faster.cms.admin.site.service;

import cn.org.faster.cms.admin.site.strategy.BaseStrategy;
import cn.org.faster.cms.admin.site.strategy.CategoryStrategy;
import cn.org.faster.cms.admin.site.strategy.IndexStrategy;
import cn.org.faster.framework.web.context.model.SpringAppContextFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangbowen
 * @since 2019-12-13
 */
@Service
@Transactional
public class SiteService {
    @Autowired
    private IndexStrategy indexStrategy;
    @Autowired
    private CategoryStrategy categoryStrategy;


    /**
     * 生成网站
     */
    public ResponseEntity generate() {
        //首页
        indexStrategy.start();
        //分类页，生成分类时，生成文章
        categoryStrategy.start();
        return new ResponseEntity(HttpStatus.OK);
    }
}
