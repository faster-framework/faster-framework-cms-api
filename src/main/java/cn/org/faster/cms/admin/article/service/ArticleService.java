package cn.org.faster.cms.admin.article.service;

import cn.org.faster.cms.admin.article.entity.Article;
import cn.org.faster.cms.admin.article.mapper.ArticleMapper;
import cn.org.faster.cms.admin.section.entity.Section;
import cn.org.faster.framework.web.exception.model.BasicErrorCode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author faster-builder
 * 文章 Service
 */
@Service
@Transactional
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {
    /**
     * 分页查询
     *
     * @param article 请求参数
     * @return 文章分页列表
     */
    public List<Article> listAll(Article article) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (article.getId() != null) {
            queryWrapper.eq(Article::getId, article.getId());
        }
        if (!StringUtils.isEmpty(article.getTitle())) {
            queryWrapper.eq(Article::getTitle, article.getTitle());
        }
        if (!StringUtils.isEmpty(article.getCode())) {
            queryWrapper.eq(Article::getCode, article.getCode());
        }
        if (!StringUtils.isEmpty(article.getDescription())) {
            queryWrapper.eq(Article::getDescription, article.getDescription());
        }
        if (!StringUtils.isEmpty(article.getImg())) {
            queryWrapper.eq(Article::getImg, article.getImg());
        }
        if (article.getSectionId() != null) {
            queryWrapper.eq(Article::getSectionId, article.getSectionId());
        }
        if (article.getPublishStatus() != null) {
            queryWrapper.eq(Article::getPublishStatus, article.getPublishStatus());
        }
        if (article.getPublishDate() != null) {
            queryWrapper.eq(Article::getPublishDate, article.getPublishDate());
        }
        if (article.getShowStatus() != null) {
            queryWrapper.eq(Article::getShowStatus, article.getShowStatus());
        }
        if (article.getTopStatus() != null) {
            queryWrapper.eq(Article::getTopStatus, article.getTopStatus());
        }
        if (article.getCreateBy() != null) {
            queryWrapper.eq(Article::getCreateBy, article.getCreateBy());
        }
        if (article.getUpdateBy() != null) {
            queryWrapper.eq(Article::getUpdateBy, article.getUpdateBy());
        }
        if (article.getCreateDate() != null) {
            queryWrapper.eq(Article::getCreateDate, article.getCreateDate());
        }
        if (article.getUpdateDate() != null) {
            queryWrapper.eq(Article::getUpdateDate, article.getUpdateDate());
        }
        if (article.getSort() != null) {
            queryWrapper.eq(Article::getSort, article.getSort());
        }
        if (!StringUtils.isEmpty(article.getRemark())) {
            queryWrapper.eq(Article::getRemark, article.getRemark());
        }
        if (article.getDeleted() != null) {
            queryWrapper.eq(Article::getDeleted, article.getDeleted());
        }
        queryWrapper.orderByDesc(Article::getTopStatus).orderByAsc(Article::getSort).orderByDesc(Article::getPublishDate);
        return super.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param article 请求参数
     * @return 文章分页列表
     */
    public IPage<Article> list(Article article) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (article.getId() != null) {
            queryWrapper.eq(Article::getId, article.getId());
        }
        if (!StringUtils.isEmpty(article.getTitle())) {
            queryWrapper.eq(Article::getTitle, article.getTitle());
        }
        if (!StringUtils.isEmpty(article.getCode())) {
            queryWrapper.eq(Article::getCode, article.getCode());
        }
        if (!StringUtils.isEmpty(article.getDescription())) {
            queryWrapper.eq(Article::getDescription, article.getDescription());
        }
        if (!StringUtils.isEmpty(article.getImg())) {
            queryWrapper.eq(Article::getImg, article.getImg());
        }
        if (article.getSectionId() != null) {
            queryWrapper.eq(Article::getSectionId, article.getSectionId());
        }
        if (article.getPublishStatus() != null) {
            queryWrapper.eq(Article::getPublishStatus, article.getPublishStatus());
        }
        if (article.getPublishDate() != null) {
            queryWrapper.eq(Article::getPublishDate, article.getPublishDate());
        }
        if (article.getShowStatus() != null) {
            queryWrapper.eq(Article::getShowStatus, article.getShowStatus());
        }
        if (article.getTopStatus() != null) {
            queryWrapper.eq(Article::getTopStatus, article.getTopStatus());
        }
        if (article.getCreateBy() != null) {
            queryWrapper.eq(Article::getCreateBy, article.getCreateBy());
        }
        if (article.getUpdateBy() != null) {
            queryWrapper.eq(Article::getUpdateBy, article.getUpdateBy());
        }
        if (article.getCreateDate() != null) {
            queryWrapper.eq(Article::getCreateDate, article.getCreateDate());
        }
        if (article.getUpdateDate() != null) {
            queryWrapper.eq(Article::getUpdateDate, article.getUpdateDate());
        }
        if (article.getSort() != null) {
            queryWrapper.eq(Article::getSort, article.getSort());
        }
        if (!StringUtils.isEmpty(article.getRemark())) {
            queryWrapper.eq(Article::getRemark, article.getRemark());
        }
        if (article.getDeleted() != null) {
            queryWrapper.eq(Article::getDeleted, article.getDeleted());
        }
        queryWrapper.orderByDesc(Article::getTopStatus).orderByAsc(Article::getSort).orderByDesc(Article::getPublishDate);

        queryWrapper.select(Article.class, tableFieldInfo -> !tableFieldInfo.getProperty().equals("content"));
        return super.baseMapper.selectPage(article.toPage(), queryWrapper);
    }

    /**
     * 根据条件查询详情
     *
     * @param article 请求参数
     * @return 文章详情
     */
    public Article query(Article article) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (article.getId() != null) {
            queryWrapper.eq(Article::getId, article.getId());
        }
        if (!StringUtils.isEmpty(article.getTitle())) {
            queryWrapper.eq(Article::getTitle, article.getTitle());
        }
        if (!StringUtils.isEmpty(article.getCode())) {
            queryWrapper.eq(Article::getCode, article.getCode());
        }
        if (!StringUtils.isEmpty(article.getDescription())) {
            queryWrapper.eq(Article::getDescription, article.getDescription());
        }
        if (!StringUtils.isEmpty(article.getImg())) {
            queryWrapper.eq(Article::getImg, article.getImg());
        }
        if (article.getSectionId() != null) {
            queryWrapper.eq(Article::getSectionId, article.getSectionId());
        }
        if (article.getPublishStatus() != null) {
            queryWrapper.eq(Article::getPublishStatus, article.getPublishStatus());
        }
        if (article.getPublishDate() != null) {
            queryWrapper.eq(Article::getPublishDate, article.getPublishDate());
        }
        if (article.getShowStatus() != null) {
            queryWrapper.eq(Article::getShowStatus, article.getShowStatus());
        }
        if (article.getTopStatus() != null) {
            queryWrapper.eq(Article::getTopStatus, article.getTopStatus());
        }
        if (article.getCreateBy() != null) {
            queryWrapper.eq(Article::getCreateBy, article.getCreateBy());
        }
        if (article.getUpdateBy() != null) {
            queryWrapper.eq(Article::getUpdateBy, article.getUpdateBy());
        }
        if (article.getCreateDate() != null) {
            queryWrapper.eq(Article::getCreateDate, article.getCreateDate());
        }
        if (article.getUpdateDate() != null) {
            queryWrapper.eq(Article::getUpdateDate, article.getUpdateDate());
        }
        if (article.getSort() != null) {
            queryWrapper.eq(Article::getSort, article.getSort());
        }
        if (!StringUtils.isEmpty(article.getRemark())) {
            queryWrapper.eq(Article::getRemark, article.getRemark());
        }
        if (article.getDeleted() != null) {
            queryWrapper.eq(Article::getDeleted, article.getDeleted());
        }
        queryWrapper.last("limit 1");
        return super.getOne(queryWrapper);
    }

    /**
     * 根据主键id查询详情
     *
     * @param id 文章id
     * @return 文章详情
     */
    public Article queryById(Long id) {
        return super.getById(id);
    }
    /**
     * 判断是否已经存在code
     *
     * @param code 权限code
     * @return true or false
     */
    private boolean existCode(String code) {
        return this.getOne(new LambdaQueryWrapper<Article>().eq(Article::getCode, code)) != null;
    }
    /**
     * 添加文章
     *
     * @param article 实体
     * @return ResponseEntity
     */
    public ResponseEntity add(Article article) {
        article.preInsert();
        //如果为已发布，设置发布时间
        if (Integer.valueOf(1).equals(article.getPublishStatus())) {
            article.setPublishDate(new Date());
        }
        if (!StringUtils.isEmpty(article.getCode())) {
            if (existCode(article.getCode())) {
                BasicErrorCode.ERROR.throwException("编码已经存在");
            }
        }
        super.save(article);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 修改文章
     *
     * @param article 实体
     * @return ResponseEntity
     */
    public ResponseEntity update(Article article) {
        article.preUpdate();
        //如果为已发布，设置发布时间
        if (Integer.valueOf(1).equals(article.getPublishStatus())) {
            article.setPublishDate(new Date());
        }
        //判断要更新的code是否已经存在
        if (!StringUtils.isEmpty(article.getCode())) {
            Article oldArticle = super.baseMapper.selectById(article.getId());
            if (oldArticle != null) {
                //如果旧的code跟要更改的code一致，说明不需要修改。如果不一致，并且数据库已经存在要更改的code，返回错误
                if (!article.getCode().equals(oldArticle.getCode()) && existCode(article.getCode())) {
                    BasicErrorCode.ERROR.throwException("编码已经存在");
                }
            }
        }
        super.updateById(article);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除文章
     *
     * @param id 主键id
     * @return ResponseEntity
     */
    public ResponseEntity delete(Long id) {
        super.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}