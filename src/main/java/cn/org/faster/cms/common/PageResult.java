package cn.org.faster.cms.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 统一分页结果。
 *
 * <p>字段名与 MyBatis-Plus {@code IPage} 保持一致，避免前端改造。</p>
 */
public record PageResult<T>(List<T> records, long total, long size, long current, long pages) {

    public static <T> PageResult<T> of(IPage<T> page) {
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getSize(),
                page.getCurrent(), page.getPages());
    }

    public static <T> PageResult<T> of(List<T> records) {
        return new PageResult<>(List.copyOf(records), records.size(), records.size(), 1, 1);
    }
}
