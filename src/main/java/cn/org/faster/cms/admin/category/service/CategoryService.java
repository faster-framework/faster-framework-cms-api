package cn.org.faster.cms.admin.category.service;

import cn.org.faster.framework.admin.permission.entity.SysPermission;
import cn.org.faster.framework.admin.permission.error.SysPermissionError;
import cn.org.faster.framework.core.entity.TreeNode;
import cn.org.faster.framework.core.utils.tree.TreeUtils;
import cn.org.faster.framework.mybatis.entity.BaseEntity;
import cn.org.faster.framework.web.exception.model.BasicErrorCode;
import cn.org.faster.framework.web.exception.model.ResponseErrorEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.org.faster.cms.admin.category.mapper.CategoryMapper;
import cn.org.faster.cms.admin.category.entity.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author faster-builder
 * 分类 Service
 */
@Service
@Transactional
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    /**
     * @return 分类树
     */
    public List<TreeNode> treeList() {
        List<Category> list = super.baseMapper.selectList(
                new LambdaQueryWrapper<Category>().orderByAsc(Category::getParentIds, Category::getSort)
        );
        return TreeUtils.convertToTree(list);
    }

    /**
     * @return 全部的分类列表
     */
    public List<Category> list() {
        return super.baseMapper.selectList(new LambdaQueryWrapper<Category>().orderByAsc(Category::getParentIds, Category::getSort));
    }

    /**
     * 根据条件查询详情
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
        return super.getOne(queryWrapper);
    }

     /**
     * 根据主键id查询详情
     * @param id 分类id
     * @return 分类详情
     */
    public Category queryById(Long id) {
        return super.getById(id);
    }
    /**
     * @param category 填充parentIds
     * @return 如果parentId不存在，返回false
     */
    private boolean completeParentIds(Category category) {
        if (category.getParentId() != 0) {
            //获取parentIds
            Category existParent = super.baseMapper.selectById(category.getParentId());
            if (existParent == null) {
                return false;
            }
            category.setParentIds(existParent.getParentIds().concat(",").concat("[").concat(existParent.getId().toString()).concat("]"));
        } else {
            category.setParentIds("[0]");
        }
        return true;
    }
    /**
     * 判断是否已经存在code
     *
     * @param code 权限code
     * @return true or false
     */
    private boolean existCode(String code) {
        return this.getOne(new LambdaQueryWrapper<Category>().eq(Category::getCode, code)) != null;
    }

    /**
    * 添加分类
    * @param category 实体
    * @return ResponseEntity
    */
    public ResponseEntity add(Category category) {
        boolean existParentId = completeParentIds(category);
        if (!existParentId) {
            BasicErrorCode.ERROR.throwException("父级不存在");
        }
        if (!StringUtils.isEmpty(category.getCode())) {
            if (existCode(category.getCode())) {
                BasicErrorCode.ERROR.throwException("编码已经存在");
            }
        }
        category.preInsert();
        super.baseMapper.insert(category);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
    * 修改分类
    * @param category 实体
    * @return ResponseEntity
    */
    public ResponseEntity update(Category category) {
        //判断要更新的code是否已经存在
        if (!StringUtils.isEmpty(category.getCode())) {
            Category oldCategory = super.baseMapper.selectById(category.getId());
            if (oldCategory != null) {
                //如果旧的code跟要更改的code一致，说明不需要修改。如果不一致，并且数据库已经存在要更改的code，返回错误
                if (!category.getCode().equals(oldCategory.getCode()) && existCode(category.getCode())) {
                    BasicErrorCode.ERROR.throwException("编码已经存在");
                }
            }
        }
        category.preUpdate();
        super.updateById(category);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除分类
     * @param id 主键id
     * @return ResponseEntity
     */
    public ResponseEntity delete(Long id) {
        super.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}