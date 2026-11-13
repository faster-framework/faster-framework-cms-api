package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
import cn.org.faster.cms.domain.SysDict;
import cn.org.faster.cms.mapper.SysDictMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 字典服务。
 */
@Service
@Transactional
public class DictService {

    private final SysDictMapper dictMapper;

    public DictService(SysDictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    public record Query(Long id, String name, String type, String dictValue, Integer showStatus) {
    }

    @Transactional(readOnly = true)
    public PageResult<SysDict> page(Query query, long current, long size) {
        Page<SysDict> page = Page.of(current < 1 ? 1 : current, size < 1 ? 20 : size);
        return PageResult.of(dictMapper.selectPage(page, wrapper(query)));
    }

    @Transactional(readOnly = true)
    public List<SysDict> listAll() {
        return dictMapper.selectList(Wrappers.<SysDict>lambdaQuery()
                .orderByAsc(SysDict::getType)
                .orderByAsc(SysDict::getSort)
                .orderByAsc(SysDict::getId));
    }

    @Transactional(readOnly = true)
    public List<SysDict> listByType(String type, boolean onlyVisible) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getType, type);
        if (onlyVisible) {
            wrapper.eq(SysDict::getShowStatus, 1);
        }
        return dictMapper.selectList(wrapper
                .orderByAsc(SysDict::getSort)
                .orderByAsc(SysDict::getId));
    }

    @Transactional(readOnly = true)
    public SysDict getRequired(Long id) {
        SysDict dict = id == null ? null : dictMapper.selectById(id);
        if (dict == null) {
            throw BizException.of(ErrorCode.NOT_FOUND, "字典不存在");
        }
        return dict;
    }

    @Transactional(readOnly = true)
    public List<SysDict> list(Query query, int max) {
        return dictMapper.selectList(wrapper(query).last("limit " + Math.max(1, max)));
    }

    public SysDict create(SysDict dict) {
        if (dict.getShowStatus() == null) {
            dict.setShowStatus(1);
        }
        dictMapper.insert(dict);
        return dict;
    }

    public SysDict update(Long id, SysDict patch) {
        getRequired(id);
        patch.setId(id);
        dictMapper.updateById(patch);
        return dictMapper.selectById(id);
    }

    public void delete(Long id) {
        getRequired(id);
        dictMapper.deleteById(id);
    }

    private LambdaQueryWrapper<SysDict> wrapper(Query query) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        if (query.id() != null) {
            wrapper.eq(SysDict::getId, query.id());
        }
        if (StringUtils.hasText(query.name())) {
            wrapper.like(SysDict::getName, query.name());
        }
        if (StringUtils.hasText(query.type())) {
            wrapper.eq(SysDict::getType, query.type());
        }
        if (StringUtils.hasText(query.dictValue())) {
            wrapper.eq(SysDict::getDictValue, query.dictValue());
        }
        if (query.showStatus() != null) {
            wrapper.eq(SysDict::getShowStatus, query.showStatus());
        }
        return wrapper.orderByAsc(SysDict::getSort).orderByAsc(SysDict::getId);
    }
}
