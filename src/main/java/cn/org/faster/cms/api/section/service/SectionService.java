package cn.org.faster.cms.api.section.service;

import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.mapper.SectionMapper;
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
 * 栏目 Service
 */
@Service
@Transactional
public class SectionService extends ServiceImpl<SectionMapper, Section> {
    /**
     * 分页查询
     *
     * @param section 请求参数
     * @return 栏目分页列表
     */
    public List<Section> listAll(Section section) {
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
        if (!StringUtils.isEmpty(section.getPosition())) {
            queryWrapper.eq(Section::getPosition, section.getPosition());
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
        if (!StringUtils.isEmpty(section.getReqType())) {
            queryWrapper.eq(Section::getReqType, section.getReqType());
        }
        if (!StringUtils.isEmpty(section.getReqLocation())) {
            queryWrapper.eq(Section::getReqLocation, section.getReqLocation());
        }
        if (section.getPublishStatus() != null) {
            queryWrapper.eq(Section::getPublishStatus, section.getPublishStatus());
        }
        if (section.getShowStatus() != null) {
            queryWrapper.eq(Section::getShowStatus, section.getShowStatus());
        }
        if (!StringUtils.isEmpty(section.getTemplatePath())) {
            queryWrapper.eq(Section::getTemplatePath, section.getTemplatePath());
        }
        if (!StringUtils.isEmpty(section.getContentTemplatePath())) {
            queryWrapper.eq(Section::getContentTemplatePath, section.getContentTemplatePath());
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
        queryWrapper.orderByAsc(Section::getSort);
        return super.baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param section 请求参数
     * @return 栏目分页列表
     */
    public IPage<Section> list(Section section) {
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
        if (!StringUtils.isEmpty(section.getPosition())) {
            queryWrapper.eq(Section::getPosition, section.getPosition());
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
        if (!StringUtils.isEmpty(section.getReqType())) {
            queryWrapper.eq(Section::getReqType, section.getReqType());
        }
        if (!StringUtils.isEmpty(section.getReqLocation())) {
            queryWrapper.eq(Section::getReqLocation, section.getReqLocation());
        }
        if (section.getPublishStatus() != null) {
            queryWrapper.eq(Section::getPublishStatus, section.getPublishStatus());
        }
        if (section.getShowStatus() != null) {
            queryWrapper.eq(Section::getShowStatus, section.getShowStatus());
        }
        if (!StringUtils.isEmpty(section.getTemplatePath())) {
            queryWrapper.eq(Section::getTemplatePath, section.getTemplatePath());
        }
        if (!StringUtils.isEmpty(section.getContentTemplatePath())) {
            queryWrapper.eq(Section::getContentTemplatePath, section.getContentTemplatePath());
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
        queryWrapper.orderByAsc(Section::getSort);
        return super.baseMapper.selectPage(section.toPage(), queryWrapper);
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
        if (!StringUtils.isEmpty(section.getPosition())) {
            queryWrapper.eq(Section::getPosition, section.getPosition());
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
        if (!StringUtils.isEmpty(section.getReqType())) {
            queryWrapper.eq(Section::getReqType, section.getReqType());
        }
        if (!StringUtils.isEmpty(section.getReqLocation())) {
            queryWrapper.eq(Section::getReqLocation, section.getReqLocation());
        }
        if (section.getPublishStatus() != null) {
            queryWrapper.eq(Section::getPublishStatus, section.getPublishStatus());
        }
        if (section.getShowStatus() != null) {
            queryWrapper.eq(Section::getShowStatus, section.getShowStatus());
        }
        if (!StringUtils.isEmpty(section.getTemplatePath())) {
            queryWrapper.eq(Section::getTemplatePath, section.getTemplatePath());
        }
        if (!StringUtils.isEmpty(section.getContentTemplatePath())) {
            queryWrapper.eq(Section::getContentTemplatePath, section.getContentTemplatePath());
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
        queryWrapper.last("limit 1");
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
     * 添加栏目
     *
     * @param section 实体
     * @return ResponseEntity
     */
    public ResponseEntity add(Section section) {
        section.preInsert();
        super.save(section);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 修改栏目
     *
     * @param section 实体
     * @return ResponseEntity
     */
    public ResponseEntity update(Section section) {
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