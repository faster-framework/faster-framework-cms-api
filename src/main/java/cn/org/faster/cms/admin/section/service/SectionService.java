package cn.org.faster.cms.admin.section.service;

import cn.org.faster.cms.admin.section.entity.Section;
import cn.org.faster.cms.admin.section.mapper.SectionMapper;
import cn.org.faster.framework.core.entity.TreeNode;
import cn.org.faster.framework.core.utils.tree.TreeUtils;
import cn.org.faster.framework.web.exception.model.BasicErrorCode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author faster-builder
 * 栏目 Service
 */
@Service
@Transactional
public class SectionService extends ServiceImpl<SectionMapper, Section> {

    /**
     * @return 栏目树
     */
    public List<TreeNode> treeList() {
        List<Section> list = super.baseMapper.selectList(
                new LambdaQueryWrapper<Section>().orderByAsc(Section::getParentIds, Section::getSort)
        );
        return TreeUtils.convertToTree(list);
    }

    /**
     * 根据条件查询详情
     *
     * @param section 请求参数
     * @return 栏目详情
     */
    public Section query(Section section) {
        LambdaQueryWrapper<Section> queryWrapper = new LambdaQueryWrapper<>();
        if (section.getId() != null) {
            queryWrapper.eq(Section::getId, section.getId());
        }
        if (!StringUtils.isEmpty(section.getName())) {
            queryWrapper.eq(Section::getName, section.getName());
        }
        if (!StringUtils.isEmpty(section.getCode())) {
            queryWrapper.eq(Section::getCode, section.getCode());
        }
        if (!StringUtils.isEmpty(section.getDescription())) {
            queryWrapper.eq(Section::getDescription, section.getDescription());
        }
        if (!StringUtils.isEmpty(section.getImg())) {
            queryWrapper.eq(Section::getImg, section.getImg());
        }
        if (section.getParentId() != null) {
            queryWrapper.eq(Section::getParentId, section.getParentId());
        }
        if (!StringUtils.isEmpty(section.getParentIds())) {
            queryWrapper.eq(Section::getParentIds, section.getParentIds());
        }
        if (section.getTemplateType() != null) {
            queryWrapper.eq(Section::getTemplateType, section.getTemplateType());
        }
        if (!StringUtils.isEmpty(section.getLink())) {
            queryWrapper.eq(Section::getLink, section.getLink());
        }
        if (section.getPublishStatus() != null) {
            queryWrapper.eq(Section::getPublishStatus, section.getPublishStatus());
        }
        if (section.getShowStatus() != null) {
            queryWrapper.eq(Section::getShowStatus, section.getShowStatus());
        }
        if (section.getTopStatus() != null) {
            queryWrapper.eq(Section::getTopStatus, section.getTopStatus());
        }
        if (!StringUtils.isEmpty(section.getTemplatePath())) {
            queryWrapper.eq(Section::getTemplatePath, section.getTemplatePath());
        }
        if (!StringUtils.isEmpty(section.getArticleTemplatePath())) {
            queryWrapper.eq(Section::getArticleTemplatePath, section.getArticleTemplatePath());
        }

        if (section.getCreateBy() != null) {
            queryWrapper.eq(Section::getCreateBy, section.getCreateBy());
        }
        if (section.getUpdateBy() != null) {
            queryWrapper.eq(Section::getUpdateBy, section.getUpdateBy());
        }
        if (section.getCreateDate() != null) {
            queryWrapper.eq(Section::getCreateDate, section.getCreateDate());
        }
        if (section.getUpdateDate() != null) {
            queryWrapper.eq(Section::getUpdateDate, section.getUpdateDate());
        }
        if (section.getSort() != null) {
            queryWrapper.eq(Section::getSort, section.getSort());
        }
        if (!StringUtils.isEmpty(section.getRemark())) {
            queryWrapper.eq(Section::getRemark, section.getRemark());
        }
        if (section.getDeleted() != null) {
            queryWrapper.eq(Section::getDeleted, section.getDeleted());
        }
        return super.getOne(queryWrapper);
    }

    /**
     * 根据主键id查询详情
     *
     * @param id 栏目id
     * @return 栏目详情
     */
    public Section queryById(Long id) {
        return super.getById(id);
    }

    /**
     * @param section 填充parentIds
     * @return 如果parentId不存在，返回false
     */
    private boolean completeParentIds(Section section) {
        if (section.getParentId() != 0) {
            //获取parentIds
            Section existParent = super.baseMapper.selectById(section.getParentId());
            if (existParent == null) {
                return false;
            }
            section.setParentIds(existParent.getParentIds().concat(",").concat("[").concat(existParent.getId().toString()).concat("]"));
        } else {
            section.setParentIds("[0]");
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
        return this.getOne(new LambdaQueryWrapper<Section>().eq(Section::getCode, code)) != null;
    }

    /**
     * 添加栏目
     *
     * @param section 实体
     * @return ResponseEntity
     */
    public ResponseEntity add(Section section) {
        boolean existParentId = completeParentIds(section);
        if (!existParentId) {
            BasicErrorCode.ERROR.throwException("父级不存在");
        }
        if (!StringUtils.isEmpty(section.getCode())) {
            if (existCode(section.getCode())) {
                BasicErrorCode.ERROR.throwException("编码已经存在");
            }
        }
        section.preInsert();
        super.baseMapper.insert(section);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 修改栏目
     *
     * @param section 实体
     * @return ResponseEntity
     */
    public ResponseEntity update(Section section) {
        //判断要更新的code是否已经存在
        if (!StringUtils.isEmpty(section.getCode())) {
            Section oldSection = super.baseMapper.selectById(section.getId());
            if (oldSection != null) {
                //如果旧的code跟要更改的code一致，说明不需要修改。如果不一致，并且数据库已经存在要更改的code，返回错误
                if (!section.getCode().equals(oldSection.getCode()) && existCode(section.getCode())) {
                    BasicErrorCode.ERROR.throwException("编码已经存在");
                }
            }
        }
        section.preUpdate();
        super.updateById(section);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除栏目
     *
     * @param id 主键id
     * @return ResponseEntity
     */
    public ResponseEntity delete(Long id) {
        super.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}