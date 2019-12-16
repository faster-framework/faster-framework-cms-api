package cn.org.faster.cms.api.category.service;

import cn.org.faster.cms.api.category.entity.Category;
import cn.org.faster.cms.api.category.mapper.CategoryMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author faster-builder
 * 分类 Service
 */
@Service
@Transactional
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {
    /**
     * 分页查询
     *
     * @param category 请求参数
     * @return 分类分页列表
     */
    public List<Category> listAll(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (category.getId() != null) {
            queryWrapper.eq(Category::getId, category.getId());
        }
        if (!StringUtils.isEmpty(category.getName())) {
            queryWrapper.eq(Category::getName, category.getName());
        }
        if (!StringUtils.isEmpty(category.getCode())) {
            queryWrapper.eq(Category::getCode, category.getCode());
        }
        if (!StringUtils.isEmpty(category.getPosition())) {
            queryWrapper.eq(Category::getPosition, category.getPosition());
        }
        if (!StringUtils.isEmpty(category.getDescription())) {
            queryWrapper.eq(Category::getDescription, category.getDescription());
        }
        if (!StringUtils.isEmpty(category.getImg())) {
            queryWrapper.eq(Category::getImg, category.getImg());
        }
        if (category.getParentId() != null) {
            queryWrapper.eq(Category::getParentId, category.getParentId());
        }
        if (!StringUtils.isEmpty(category.getParentIds())) {
            queryWrapper.eq(Category::getParentIds, category.getParentIds());
        }
        if (!StringUtils.isEmpty(category.getReqType())) {
            queryWrapper.eq(Category::getReqType, category.getReqType());
        }
        if (!StringUtils.isEmpty(category.getReqLocation())) {
            queryWrapper.eq(Category::getReqLocation, category.getReqLocation());
        }
        if (category.getPublishStatus() != null) {
            queryWrapper.eq(Category::getPublishStatus, category.getPublishStatus());
        }
        if (category.getShowStatus() != null) {
            queryWrapper.eq(Category::getShowStatus, category.getShowStatus());
        }
        if (!StringUtils.isEmpty(category.getTemplatePath())) {
            queryWrapper.eq(Category::getTemplatePath, category.getTemplatePath());
        }
        if (!StringUtils.isEmpty(category.getContentTemplatePath())) {
            queryWrapper.eq(Category::getContentTemplatePath, category.getContentTemplatePath());
        }
        if (category.getCreateBy() != null) {
            queryWrapper.eq(Category::getCreateBy, category.getCreateBy());
        }
        if (category.getUpdateBy() != null) {
            queryWrapper.eq(Category::getUpdateBy, category.getUpdateBy());
        }
        if (category.getCreateDate() != null) {
            queryWrapper.eq(Category::getCreateDate, category.getCreateDate());
        }
        if (category.getUpdateDate() != null) {
            queryWrapper.eq(Category::getUpdateDate, category.getUpdateDate());
        }
        if (category.getSort() != null) {
            queryWrapper.eq(Category::getSort, category.getSort());
        }
        if (!StringUtils.isEmpty(category.getRemark())) {
            queryWrapper.eq(Category::getRemark, category.getRemark());
        }
        if (category.getDeleted() != null) {
            queryWrapper.eq(Category::getDeleted, category.getDeleted());
        }
        queryWrapper.orderByAsc(Category::getSort);
        return super.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param category 请求参数
     * @return 分类分页列表
     */
    public IPage<Category> list(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (category.getId() != null) {
            queryWrapper.eq(Category::getId, category.getId());
        }
        if (!StringUtils.isEmpty(category.getName())) {
            queryWrapper.eq(Category::getName, category.getName());
        }
        if (!StringUtils.isEmpty(category.getCode())) {
            queryWrapper.eq(Category::getCode, category.getCode());
        }
        if (!StringUtils.isEmpty(category.getPosition())) {
            queryWrapper.eq(Category::getPosition, category.getPosition());
        }
        if (!StringUtils.isEmpty(category.getDescription())) {
            queryWrapper.eq(Category::getDescription, category.getDescription());
        }
        if (!StringUtils.isEmpty(category.getImg())) {
            queryWrapper.eq(Category::getImg, category.getImg());
        }
        if (category.getParentId() != null) {
            queryWrapper.eq(Category::getParentId, category.getParentId());
        }
        if (!StringUtils.isEmpty(category.getParentIds())) {
            queryWrapper.eq(Category::getParentIds, category.getParentIds());
        }
        if (!StringUtils.isEmpty(category.getReqType())) {
            queryWrapper.eq(Category::getReqType, category.getReqType());
        }
        if (!StringUtils.isEmpty(category.getReqLocation())) {
            queryWrapper.eq(Category::getReqLocation, category.getReqLocation());
        }
        if (category.getPublishStatus() != null) {
            queryWrapper.eq(Category::getPublishStatus, category.getPublishStatus());
        }
        if (category.getShowStatus() != null) {
            queryWrapper.eq(Category::getShowStatus, category.getShowStatus());
        }
        if (!StringUtils.isEmpty(category.getTemplatePath())) {
            queryWrapper.eq(Category::getTemplatePath, category.getTemplatePath());
        }
        if (!StringUtils.isEmpty(category.getContentTemplatePath())) {
            queryWrapper.eq(Category::getContentTemplatePath, category.getContentTemplatePath());
        }
        if (category.getCreateBy() != null) {
            queryWrapper.eq(Category::getCreateBy, category.getCreateBy());
        }
        if (category.getUpdateBy() != null) {
            queryWrapper.eq(Category::getUpdateBy, category.getUpdateBy());
        }
        if (category.getCreateDate() != null) {
            queryWrapper.eq(Category::getCreateDate, category.getCreateDate());
        }
        if (category.getUpdateDate() != null) {
            queryWrapper.eq(Category::getUpdateDate, category.getUpdateDate());
        }
        if (category.getSort() != null) {
            queryWrapper.eq(Category::getSort, category.getSort());
        }
        if (!StringUtils.isEmpty(category.getRemark())) {
            queryWrapper.eq(Category::getRemark, category.getRemark());
        }
        if (category.getDeleted() != null) {
            queryWrapper.eq(Category::getDeleted, category.getDeleted());
        }
        queryWrapper.orderByAsc(Category::getSort);
        return super.baseMapper.selectPage(category.toPage(), queryWrapper);
    }

    /**
     * 根据条件查询详情
     *
     * @param category 请求参数
     * @return 分类详情
     */
    public Category query(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (category.getId() != null) {
            queryWrapper.eq(Category::getId, category.getId());
        }
        if (!StringUtils.isEmpty(category.getName())) {
            queryWrapper.eq(Category::getName, category.getName());
        }
        if (!StringUtils.isEmpty(category.getCode())) {
            queryWrapper.eq(Category::getCode, category.getCode());
        }
        if (!StringUtils.isEmpty(category.getPosition())) {
            queryWrapper.eq(Category::getPosition, category.getPosition());
        }
        if (!StringUtils.isEmpty(category.getDescription())) {
            queryWrapper.eq(Category::getDescription, category.getDescription());
        }
        if (!StringUtils.isEmpty(category.getImg())) {
            queryWrapper.eq(Category::getImg, category.getImg());
        }
        if (category.getParentId() != null) {
            queryWrapper.eq(Category::getParentId, category.getParentId());
        }
        if (!StringUtils.isEmpty(category.getParentIds())) {
            queryWrapper.eq(Category::getParentIds, category.getParentIds());
        }
        if (!StringUtils.isEmpty(category.getReqType())) {
            queryWrapper.eq(Category::getReqType, category.getReqType());
        }
        if (!StringUtils.isEmpty(category.getReqLocation())) {
            queryWrapper.eq(Category::getReqLocation, category.getReqLocation());
        }
        if (category.getPublishStatus() != null) {
            queryWrapper.eq(Category::getPublishStatus, category.getPublishStatus());
        }
        if (category.getShowStatus() != null) {
            queryWrapper.eq(Category::getShowStatus, category.getShowStatus());
        }
        if (!StringUtils.isEmpty(category.getTemplatePath())) {
            queryWrapper.eq(Category::getTemplatePath, category.getTemplatePath());
        }
        if (!StringUtils.isEmpty(category.getContentTemplatePath())) {
            queryWrapper.eq(Category::getContentTemplatePath, category.getContentTemplatePath());
        }
        if (category.getCreateBy() != null) {
            queryWrapper.eq(Category::getCreateBy, category.getCreateBy());
        }
        if (category.getUpdateBy() != null) {
            queryWrapper.eq(Category::getUpdateBy, category.getUpdateBy());
        }
        if (category.getCreateDate() != null) {
            queryWrapper.eq(Category::getCreateDate, category.getCreateDate());
        }
        if (category.getUpdateDate() != null) {
            queryWrapper.eq(Category::getUpdateDate, category.getUpdateDate());
        }
        if (category.getSort() != null) {
            queryWrapper.eq(Category::getSort, category.getSort());
        }
        if (!StringUtils.isEmpty(category.getRemark())) {
            queryWrapper.eq(Category::getRemark, category.getRemark());
        }
        if (category.getDeleted() != null) {
            queryWrapper.eq(Category::getDeleted, category.getDeleted());
        }
        queryWrapper.last("limit 1");
        return super.getOne(queryWrapper);
    }

    /**
     * 根据主键id查询详情
     *
     * @param id 分类id
     * @return 分类详情
     */
    public Category queryById(Long id) {
        return super.getById(id);
    }

    /**
     * 添加分类
     *
     * @param category 实体
     * @return ResponseEntity
     */
    public ResponseEntity add(Category category) {
        category.preInsert();
        super.save(category);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 修改分类
     *
     * @param category 实体
     * @return ResponseEntity
     */
    public ResponseEntity update(Category category) {
        category.preUpdate();
        super.updateById(category);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除分类
     *
     * @param id 主键id
     * @return ResponseEntity
     */
    public ResponseEntity delete(Long id) {
        super.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}