package cn.org.faster.cms.admin.site.service;

import cn.org.faster.cms.admin.site.strategy.impl.SectionStrategy;
import cn.org.faster.cms.admin.site.strategy.impl.IndexStrategy;
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
    private SectionStrategy sectionStrategy;


    /**
     * 生成网站
     */
    public ResponseEntity generate() {
        //首页
        indexStrategy.start();
        //栏目页，生成栏目时，生成文章
        sectionStrategy.start();
        return new ResponseEntity(HttpStatus.OK);
    }
}
