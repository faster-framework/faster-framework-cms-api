package cn.org.faster.cms.api.article.service;

import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.mapper.ArticleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public IPage<Article> list(Article article) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (article.getId() != null) {
            queryWrapper.eq(Article::getId, article.getId());
        }
        if (!StringUtils.isEmpty(article.getTitle())) {
            queryWrapper.eq(Article::getTitle, article.getTitle());
        }
        if (!StringUtils.isEmpty(article.getDescription())) {
            queryWrapper.eq(Article::getDescription, article.getDescription());
        }
        if (!StringUtils.isEmpty(article.getImg())) {
            queryWrapper.eq(Article::getImg, article.getImg());
        }
        if (article.getCategoryId() != null) {
            queryWrapper.eq(Article::getCategoryId, article.getCategoryId());
        }
        if (article.getPublishStatus() != null) {
            queryWrapper.eq(Article::getPublishStatus, article.getPublishStatus());
        }
        if (article.getPublishDate() != null) {
            queryWrapper.eq(Article::getPublishDate, article.getPublishDate());
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
        queryWrapper.orderByDesc(Article::getPublishDate).orderByAsc(Article::getSort);
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
        if (!StringUtils.isEmpty(article.getDescription())) {
            queryWrapper.eq(Article::getDescription, article.getDescription());
        }
        if (!StringUtils.isEmpty(article.getImg())) {
            queryWrapper.eq(Article::getImg, article.getImg());
        }
        if (article.getCategoryId() != null) {
            queryWrapper.eq(Article::getCategoryId, article.getCategoryId());
        }
        if (article.getPublishStatus() != null) {
            queryWrapper.eq(Article::getPublishStatus, article.getPublishStatus());
        }
        if (article.getPublishDate() != null) {
            queryWrapper.eq(Article::getPublishDate, article.getPublishDate());
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
     * 添加文章
     *
     * @param article 实体
     * @return ResponseEntity
     */
    public ResponseEntity add(Article article) {
        article.preInsert();
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